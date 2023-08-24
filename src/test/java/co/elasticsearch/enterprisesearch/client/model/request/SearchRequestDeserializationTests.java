package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.Geolocation;
import co.elasticsearch.enterprisesearch.client.model.Sort;
import co.elasticsearch.enterprisesearch.client.model.request.search.*;
import co.elasticsearch.enterprisesearch.client.model.request.search.boost.*;
import co.elasticsearch.enterprisesearch.client.model.request.search.facet.Facet;
import co.elasticsearch.enterprisesearch.client.model.request.search.facet.FacetSortField;
import co.elasticsearch.enterprisesearch.client.model.request.search.facet.SearchRangeFacet;
import co.elasticsearch.enterprisesearch.client.model.request.search.facet.ValueFacet;
import co.elasticsearch.enterprisesearch.client.model.request.search.filter.*;
import co.elasticsearch.enterprisesearch.client.model.request.search.range.NumberRange;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static co.elasticsearch.enterprisesearch.client.model.request.search.filter.DateValueFilter.RFC_3339;
import static org.junit.jupiter.api.Assertions.*;

class SearchRequestDeserializationTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void test1() throws JsonProcessingException{
        String requestExample = TestUtil.readResourceFile("examples/requests/testRequest.json");
        SearchRequest request = objectMapper.readValue(requestExample,SearchRequest.class);
        assertEquals("test",request.getQuery());
    }

    @Test
    void basicProperties() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/requests/basicProperties.json");
        SearchRequest request = objectMapper.readValue(requestExample, SearchRequest.class);
        assertEquals("Parks",request.getQuery());
        assertEquals(10,request.getPrecision());
        assertEquals(true,request.getRecordAnalytics());
    }

    @Test
    void queryWithPage() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/requests/queryWithPage.json");
        SearchRequest request = objectMapper.readValue(requestExample, SearchRequest.class);
        assertEquals("This is a test",request.getQuery());
        assertEquals(200,request.getPage().getSize());
        assertEquals(100,request.getPage().getCurrent());
    }

    @Test
    void queryWithSorts() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/requests/sorting.json");
        SearchRequest request = objectMapper.readValue(requestExample, SearchRequest.class);
        assertEquals("This is a test",request.getQuery());
        Sort score = request.getSort().get(0);
        assertEquals(Sort.SCORE,score.getName());
        assertEquals(Sort.Order.ASCENDING,score.getDirection());

        Sort title = request.getSort().get(1);
        assertEquals("title",title.getName());
        assertEquals(Sort.Order.DESCENDING,title.getDirection());

        Sort geo = request.getSort().get(2);
        assertEquals("location",geo.getName());
        GeolocationSort geoSort= (GeolocationSort) geo.getDirection();
        assertEquals(Sort.Order.DESCENDING,geoSort.getOrder());

    }

    @Test
    void queryWithGroup() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/requests/group.json");
        SearchRequest request = objectMapper.readValue(requestExample, SearchRequest.class);
        Group group = request.getGroup();
        assertEquals("states",group.getField());
        assertEquals(4,group.getSize());
        assertEquals(new Sort(Sort.SCORE, Sort.Order.ASCENDING),group.getSort());
        assertEquals(false,group.getCollapse());
    }

    @Test
    void filterSimple() throws JsonProcessingException {
        String simpleFilter = TestUtil.readResourceFile("examples/requests/filterSimple.json");
        SearchRequest simpleFilterRequest = objectMapper.readValue(simpleFilter, SearchRequest.class);
        Filters whcFilter = simpleFilterRequest.getFilters();
        Optional<TextValueFilter> whcTextFilter = whcFilter.getFieldFilter(TextValueFilter.class);
        assertTrue(whcTextFilter.isPresent());
        assertEquals("world_heritage_site", whcTextFilter.get().getName());
        assertEquals("true", whcTextFilter.get().getValues().get(0));
    }
    @Test
    void filterArray() throws JsonProcessingException {
        String arrayFilter = TestUtil.readResourceFile("examples/requests/filterArray.json");
        SearchRequest request = objectMapper.readValue(arrayFilter, SearchRequest.class);
        Filters whcFilter = request.getFilters();
        Optional<TextValueFilter> whcTextFilter = whcFilter.getFieldFilter(TextValueFilter.class);
        assertTrue(whcTextFilter.isPresent());
        assertEquals("states", whcTextFilter.get().getName());
        assertIterableEquals(List.of("California", "Alaska"), whcTextFilter.get().getValues());
    }
    @Test
    void filterDateRange() throws JsonProcessingException {
        String dateRangeFilter = TestUtil.readResourceFile("examples/requests/filterDateRange.json");
        SearchRequest dateRangeRequest = objectMapper.readValue(dateRangeFilter, SearchRequest.class);
        Filters dateEstablishedFilter = dateRangeRequest.getFilters();
        Optional<DateRangeFilter> dateRange = dateEstablishedFilter.getFieldFilter(DateRangeFilter.class);
        assertTrue(dateRange.isPresent());
        assertEquals("date_established",dateRange.get().getName());
        assertEquals(OffsetDateTime.parse("1900-01-01T12:00:00+00:00", RFC_3339),dateRange.get().getFrom());
        assertEquals(OffsetDateTime.parse("1950-01-01T00:00:00+00:00", RFC_3339),dateRange.get().getTo());
    }

    @Test
    void filterGeolocation() throws JsonProcessingException {
        String filterJson = TestUtil.readResourceFile("examples/requests/filterGeoLocation.json");
        SearchRequest request = objectMapper.readValue(filterJson, SearchRequest.class);
        Optional<GeolocationFilter> geolocationFilter = request.getFilters().getFieldFilter(GeolocationFilter.class);
        assertTrue(geolocationFilter.isPresent());
        assertEquals("location",geolocationFilter.get().getName());
        assertEquals(new Geolocation("37.386483","-122.083842"),geolocationFilter.get().getCenter());
        assertEquals(new BigDecimal(300),geolocationFilter.get().getDistance());
        assertEquals(Geolocation.Unit.KILOMETERS,geolocationFilter.get().getUnit());
    }
    @Test
    void filterBoolean() throws JsonProcessingException {
        String dateRangeFilter = TestUtil.readResourceFile("examples/requests/filterBoolean.json");
        SearchRequest dateRangeRequest = objectMapper.readValue(dateRangeFilter, SearchRequest.class);
        Optional<BooleanFilter> booleanFilter = dateRangeRequest.getFilters().getBooleanFilter();
        assertTrue(booleanFilter.isPresent());
        List<Filter> all = booleanFilter.get().getAll();
        assertEquals(new TextValueFilter("states","California"),all.get(0));
        assertEquals(new TextValueFilter("world_heritage_site","true"),all.get(1));

        List<Filter> any = booleanFilter.get().getAny();
        assertEquals(new NumberRangeFilter("acres",40000,null),any.get(0));
        assertEquals(new NumberRangeFilter("square_km",500, null),any.get(1));

        List<Filter> none = booleanFilter.get().getNone();
        assertEquals(new TextValueFilter("title","Yosemite"),none.get(0));

    }

    @Test
    void filterNested() throws JsonProcessingException {
        String filterString = TestUtil.readResourceFile("examples/requests/filterNested.json");
        SearchRequest nestedRequest = objectMapper.readValue(filterString, SearchRequest.class);
        Optional<BooleanFilter> booleanFilter = nestedRequest.getFilters().getBooleanFilter();
        assertTrue(booleanFilter.isPresent());

        Filter nested = booleanFilter.get().getAny().get(0);
        assertInstanceOf(BooleanFilter.class,nested);
        BooleanFilter nestedFilter = (BooleanFilter) nested;
        assertEquals(new TextValueFilter("states",List.of("California")),nestedFilter.getAll().get(0));
        assertEquals(new TextValueFilter("world_heritage_site", "true"),nestedFilter.getAll().get(1));
    }

    @Test
    void boostTextValue() throws JsonProcessingException {
        String boostString = TestUtil.readResourceFile("examples/requests/boostText.json");
        SearchRequest boostRequest = objectMapper.readValue(boostString, SearchRequest.class);
        Optional<Boost> oBoost = boostRequest.getBoosts().stream().filter(b -> b.getName().equals("world_heritage_site")).findFirst();
        assertTrue(oBoost.isPresent());
        Boost boost = oBoost.get();
        assertInstanceOf(TextValueBoost.class,boost);
        TextValueBoost valueBoost = (TextValueBoost) boost;
        assertEquals(Boost.Operation.MULTIPLY,valueBoost.getOperation());
        assertEquals(new BigDecimal(10),valueBoost.getFactor());
        assertEquals(Boost.BoostType.VALUE,valueBoost.getType());
        assertIterableEquals(List.of("true","false"),valueBoost.getValue());
    }

    @Test
    void boostNumericValue() throws JsonProcessingException{
        String boostString = TestUtil.readResourceFile("examples/requests/boostNumeric.json");
        SearchRequest boostRequest = objectMapper.readValue(boostString, SearchRequest.class);
        Optional<Boost> oBoost = boostRequest.getBoosts().stream().filter(b -> b.getName().equals("acres")).findFirst();
        assertTrue(oBoost.isPresent());
        Boost boost = oBoost.get();
        assertInstanceOf(NumericValueBoost.class,boost);
        NumericValueBoost valueBoost = (NumericValueBoost) boost;
        assertEquals(Boost.Operation.ADD,valueBoost.getOperation());
        assertEquals(new BigDecimal("2.3"),valueBoost.getFactor());
        assertEquals(Boost.BoostType.VALUE,valueBoost.getType());
        assertIterableEquals(List.of(new BigDecimal("10"),new BigDecimal("20.2")),valueBoost.getValue());
    }

    @Test
    void boostDateValue() throws JsonProcessingException{
        String boostString = TestUtil.readResourceFile("examples/requests/boostDate.json");
        SearchRequest boostRequest = objectMapper.readValue(boostString, SearchRequest.class);
        Optional<Boost> oBoost = boostRequest.getBoosts().stream().filter(b -> b.getName().equals("date_established")).findFirst();
        assertTrue(oBoost.isPresent());
        Boost boost = oBoost.get();
        assertInstanceOf(DateValueBoost.class,boost);
        DateValueBoost valueBoost = (DateValueBoost) boost;
        assertEquals(Boost.Operation.ADD,valueBoost.getOperation());
        assertEquals(new BigDecimal("1.0"),valueBoost.getFactor());
        assertEquals(Boost.BoostType.VALUE,valueBoost.getType());
        assertIterableEquals(List.of(OffsetDateTime.parse("1901-01-22T00:00:00Z",RFC_3339)),valueBoost.getValue());
    }

    @Test
    void boostFunctional() throws JsonProcessingException{
        String boostString = TestUtil.readResourceFile("examples/requests/boostFunctional.json");
        SearchRequest boostRequest = objectMapper.readValue(boostString, SearchRequest.class);
        Optional<Boost> oBoost = boostRequest.getBoosts().stream().filter(b -> b.getName().equals("visitors")).findFirst();
        assertTrue(oBoost.isPresent());
        Boost boost = oBoost.get();
        assertInstanceOf(FunctionalBoost.class,boost);
        FunctionalBoost functionalBoost = (FunctionalBoost) boost;
        assertEquals(Boost.Operation.MULTIPLY,functionalBoost.getOperation());
        assertEquals(new BigDecimal("2"),functionalBoost.getFactor());
        assertEquals(Boost.Function.LOGARITHMIC,functionalBoost.getFunction());
        assertEquals(Boost.BoostType.FUNCTIONAL,functionalBoost.getType());
    }

    @Test
    void boostNumericProximity() throws JsonProcessingException{
        String boostString = TestUtil.readResourceFile("examples/requests/boostNumericProximity.json");
        SearchRequest boostRequest = objectMapper.readValue(boostString, SearchRequest.class);
        Optional<Boost> oBoost = boostRequest.getBoosts().stream().filter(b -> b.getName().equals("acres")).findFirst();
        assertTrue(oBoost.isPresent());
        Boost boost = oBoost.get();
        assertInstanceOf(NumberProximityBoost.class,boost);
        NumberProximityBoost numBoost = (NumberProximityBoost) boost;
        assertEquals(new BigDecimal(130),numBoost.getCenter());
        assertEquals(new BigDecimal("7.1"),numBoost.getFactor());
        assertEquals(Boost.Function.GAUSSIAN,numBoost.getFunction());
        assertEquals(Boost.BoostType.PROXIMITY,numBoost.getType());
    }

    @Test
    void boostGeolocationProximity() throws JsonProcessingException{
        String boostString = TestUtil.readResourceFile("examples/requests/boostGeolocation.json");
        SearchRequest boostRequest = objectMapper.readValue(boostString, SearchRequest.class);
        Optional<Boost> oBoost = boostRequest.getBoosts().stream().filter(b -> b.getName().equals("location")).findFirst();
        assertTrue(oBoost.isPresent());
        Boost boost = oBoost.get();
        assertInstanceOf(GeolocationProximityBoost.class,boost);
        GeolocationProximityBoost geoBoost = (GeolocationProximityBoost) boost;
        assertEquals(new Geolocation(new BigDecimal("25.32"),new BigDecimal("-80.93")),geoBoost.getCenter());
        assertEquals(new BigDecimal(8),geoBoost.getFactor());
        assertEquals(Boost.Function.LINEAR,geoBoost.getFunction());
        assertEquals(Boost.BoostType.PROXIMITY,geoBoost.getType());
    }

    @Test
    void boostRecency() throws JsonProcessingException{
        String boostString = TestUtil.readResourceFile("examples/requests/boostRecency.json");
        SearchRequest boostRequest = objectMapper.readValue(boostString, SearchRequest.class);
        Optional<Boost> oBoost = boostRequest.getBoosts().stream().filter(b -> b.getName().equals("date_established")).findFirst();
        assertTrue(oBoost.isPresent());
        Boost boost = oBoost.get();
        assertInstanceOf(RecencyBoost.class,boost);
        RecencyBoost recencyBoost = (RecencyBoost) boost;
        assertTrue(recencyBoost.isUseNow());
        assertEquals("now",recencyBoost.getCenter());
        assertEquals(new BigDecimal(8),recencyBoost.getFactor());
        assertEquals(Boost.Function.LINEAR,recencyBoost.getFunction());
        assertEquals(Boost.BoostType.PROXIMITY,recencyBoost.getType());
    }

    @Test
    void searchFields() throws JsonProcessingException{
        String searchFieldString = TestUtil.readResourceFile("examples/requests/searchField.json");
        SearchRequest searchFieldRequest = objectMapper.readValue(searchFieldString, SearchRequest.class);
        List<SearchField> fields = searchFieldRequest.getSearchFields();
        SearchField titleField = fields.get(0);
        assertEquals(new SearchField("title").setWeight(new BigDecimal("10")),titleField);
        SearchField descriptionField = fields.get(1);
        assertEquals(new SearchField("description").setWeight(new BigDecimal("5")),descriptionField);
        SearchField statesField = fields.get(2);
        assertEquals(new SearchField("states"),statesField);
    }

    @Test
    void resultFields() throws JsonProcessingException{
        String searchFieldString = TestUtil.readResourceFile("examples/requests/resultFields.json");
        SearchRequest searchFieldRequest = objectMapper.readValue(searchFieldString, SearchRequest.class);
        List<ResultField> resultFields = searchFieldRequest.getResultFields();
        ResultField title = resultFields.get(0);
        assertEquals(new ResultField("title").withRaw().withoutSnippet(),title);
        ResultField description = resultFields.get(1);
        assertEquals(new ResultField("description").withoutRaw().withSnippet(50,true),description);
        ResultField states = resultFields.get(2);
        assertEquals(new ResultField("states").withRaw(100).withSnippet(20,false),states);
    }

    @Test
    void analytics() throws JsonProcessingException {
        String searchFieldString = TestUtil.readResourceFile("examples/requests/analyticsTags.json");
        SearchRequest analyticsRequest = objectMapper.readValue(searchFieldString, SearchRequest.class);
        assertIterableEquals(List.of("web","mobile"),analyticsRequest.getAnalytics().getTags());
    }
    @Test
    void facetValues() throws JsonProcessingException {
        String searchFieldString = TestUtil.readResourceFile("examples/requests/facetText.json");
        SearchRequest facetsRequest = objectMapper.readValue(searchFieldString, SearchRequest.class);
        Optional<Facet> optionalStates = facetsRequest.getFacets().stream().filter(f -> f.getFieldName().equals("states")).findFirst();
        assertTrue(optionalStates.isPresent());
        assertInstanceOf(ValueFacet.class,optionalStates.get());
        ValueFacet valueFacet = (ValueFacet) optionalStates.get();
        assertEquals("value",valueFacet.getType().getValue());
        assertEquals("top-five-states",valueFacet.getName());
        assertEquals(5,valueFacet.getSize());
        assertEquals(FacetSortField.COUNT,valueFacet.getSortField());
    }

    @Test
    void facetRange() throws JsonProcessingException {
        String searchFieldString = TestUtil.readResourceFile("examples/requests/facetRange.json");
        SearchRequest facetsRequest = objectMapper.readValue(searchFieldString, SearchRequest.class);
        Optional<Facet> acres = facetsRequest.getFacets().stream().filter(f -> f.getFieldName().equals("acres")).findFirst();
        assertTrue(acres.isPresent());
        assertInstanceOf(SearchRangeFacet.class,acres.get());
        SearchRangeFacet acreFacet = (SearchRangeFacet) acres.get();
        assertEquals("range",acreFacet.getType().getValue());
        assertEquals("min-and-max-range",acreFacet.getName());
        assertEquals(new NumberRange().setFrom(new BigDecimal(1)).setTo(new BigDecimal(10000)),acreFacet.getRanges().get(0));

    }
}
