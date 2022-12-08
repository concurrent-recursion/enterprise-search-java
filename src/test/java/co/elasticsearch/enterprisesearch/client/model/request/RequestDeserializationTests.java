package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.GeoLocation;
import co.elasticsearch.enterprisesearch.client.model.Sort;
import co.elasticsearch.enterprisesearch.client.model.request.boost.*;
import co.elasticsearch.enterprisesearch.client.model.request.filter.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static co.elasticsearch.enterprisesearch.client.model.request.filter.DateValueFilter.RFC_3339;
import static org.junit.jupiter.api.Assertions.*;

class RequestDeserializationTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void basicProperties() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/requests/basicProperties.json");
        SearchApiRequest request = objectMapper.readValue(requestExample,SearchApiRequest.class);
        assertEquals("Parks",request.getQuery());
        assertEquals(10,request.getPrecision());
        assertEquals(true,request.getRecordAnalytics());
    }

    @Test
    void queryWithPage() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/requests/queryWithPage.json");
        SearchApiRequest request = objectMapper.readValue(requestExample,SearchApiRequest.class);
        assertEquals("This is a test",request.getQuery());
        assertEquals(200,request.getPage().getSize());
        assertEquals(100,request.getPage().getCurrent());
    }

    @Test
    void queryWithSorts() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/requests/sorting.json");
        SearchApiRequest request = objectMapper.readValue(requestExample,SearchApiRequest.class);
        assertEquals("This is a test",request.getQuery());
        Sort score = request.getSort().get(0);
        assertEquals(Sort.SCORE,score.getName());
        assertEquals(Sort.Direction.ASCENDING,score.getDirection());

        Sort title = request.getSort().get(1);
        assertEquals("title",title.getName());
        assertEquals(Sort.Direction.DESCENDING,title.getDirection());

        Sort geo = request.getSort().get(2);
        assertEquals("location",geo.getName());
        GeoLocationSort geoSort= (GeoLocationSort) geo.getDirection();
        assertEquals(Sort.Direction.DESCENDING,geoSort.getOrder());

    }

    @Test
    void queryWithGroup() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/requests/group.json");
        SearchApiRequest request = objectMapper.readValue(requestExample,SearchApiRequest.class);
        Group group = request.getGroup();
        assertEquals("states",group.getField());
        assertEquals(4,group.getSize());
        assertEquals(new Sort(Sort.SCORE,Sort.Direction.ASCENDING),group.getSort());
        assertEquals(false,group.getCollapse());
    }

    @Test
    void filterSimple() throws JsonProcessingException {
        String simpleFilter = TestUtil.readResourceFile("examples/requests/filterSimple.json");
        SearchApiRequest simpleFilterRequest = objectMapper.readValue(simpleFilter, SearchApiRequest.class);
        Filter whcFilter = simpleFilterRequest.getFilters();
        assertInstanceOf(TextValueFilter.class,whcFilter);
        TextValueFilter whcTextFilter = (TextValueFilter) whcFilter;
        assertEquals("world_heritage_site", whcTextFilter.getName());
        assertEquals("true", whcTextFilter.getValues().get(0));
    }
    @Test
    void filterArray() throws JsonProcessingException {

        String arrayFilter = TestUtil.readResourceFile("examples/requests/filterArray.json");
        SearchApiRequest request = objectMapper.readValue(arrayFilter, SearchApiRequest.class);
        Filter filter = request.getFilters();
        assertInstanceOf(TextValueFilter.class,filter);
        TextValueFilter f = (TextValueFilter) filter;
        assertEquals("states", f.getName());
        assertIterableEquals(List.of("California", "Alaska"), f.getValues());
    }
    @Test
    void filterDateRange() throws JsonProcessingException {
        String dateRangeFilter = TestUtil.readResourceFile("examples/requests/filterDateRange.json");
        SearchApiRequest dateRangeRequest = objectMapper.readValue(dateRangeFilter,SearchApiRequest.class);
        Filter dateEstablishedFilter = dateRangeRequest.getFilters();
        assertInstanceOf(DateRangeFilter.class,dateEstablishedFilter);
        DateRangeFilter dateRange = (DateRangeFilter) dateEstablishedFilter;
        assertEquals("date_established",dateRange.getName());
        assertEquals(OffsetDateTime.parse("1900-01-01T12:00:00+00:00", RFC_3339),dateRange.getFrom());
        assertEquals(OffsetDateTime.parse("1950-01-01T00:00:00+00:00", RFC_3339),dateRange.getTo());
    }

    @Test
    void filterGeolocation() throws JsonProcessingException {
        String filterJson = TestUtil.readResourceFile("examples/requests/filterGeolocation.json");
        SearchApiRequest request = objectMapper.readValue(filterJson,SearchApiRequest.class);
        Filter abstractFilter = request.getFilters();
        assertInstanceOf(GeolocationFilter.class,abstractFilter);
        GeolocationFilter geolocationFilter = (GeolocationFilter) abstractFilter;
        assertEquals("location",geolocationFilter.getName());
        assertEquals(new GeoLocation("37.386483","-122.083842"),geolocationFilter.getCenter());
        assertEquals(new BigDecimal(300),geolocationFilter.getDistance());
        assertEquals(GeoLocation.Unit.KILOMETERS,geolocationFilter.getUnit());
    }
    @Test
    void filterBoolean() throws JsonProcessingException {
        String dateRangeFilter = TestUtil.readResourceFile("examples/requests/filterBoolean.json");
        SearchApiRequest dateRangeRequest = objectMapper.readValue(dateRangeFilter,SearchApiRequest.class);
        Filter rootFilter = dateRangeRequest.getFilters();
        assertInstanceOf(BooleanFilter.class,rootFilter);
        BooleanFilter booleanFilter = (BooleanFilter) rootFilter;
        List<Filter> all = booleanFilter.getAll();
        assertEquals(new TextValueFilter("states","California"),all.get(0));
        assertEquals(new TextValueFilter("world_heritage_site","true"),all.get(1));

        List<Filter> any = booleanFilter.getAny();
        assertEquals(new NumberRangeFilter("acres",40000,null),any.get(0));
        assertEquals(new NumberRangeFilter("square_km",500, null),any.get(1));

        List<Filter> none = booleanFilter.getNone();
        assertEquals(new TextValueFilter("title","Yosemite"),none.get(0));

    }

    @Test
    void filterNested() throws JsonProcessingException {
        String filterString = TestUtil.readResourceFile("examples/requests/filterNested.json");
        SearchApiRequest nestedRequest = objectMapper.readValue(filterString, SearchApiRequest.class);
        Filter rootFilter = nestedRequest.getFilters();
        assertInstanceOf(BooleanFilter.class,rootFilter);
        BooleanFilter booleanFilter = (BooleanFilter) rootFilter;

        Filter nested = booleanFilter.getAny().get(0);
        assertInstanceOf(BooleanFilter.class,nested);
        BooleanFilter nestedFilter = (BooleanFilter) nested;
        assertEquals(new TextValueFilter("states",List.of("California")),nestedFilter.getAll().get(0));
        assertEquals(new TextValueFilter("world_heritage_site", "true"),nestedFilter.getAll().get(1));
    }

    @Test
    void boostTextValue() throws JsonProcessingException {
        String boostString = TestUtil.readResourceFile("examples/requests/boostText.json");
        SearchApiRequest boostRequest = objectMapper.readValue(boostString, SearchApiRequest.class);
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
        SearchApiRequest boostRequest = objectMapper.readValue(boostString, SearchApiRequest.class);
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
        SearchApiRequest boostRequest = objectMapper.readValue(boostString, SearchApiRequest.class);
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
        SearchApiRequest boostRequest = objectMapper.readValue(boostString, SearchApiRequest.class);
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
        SearchApiRequest boostRequest = objectMapper.readValue(boostString, SearchApiRequest.class);
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
        SearchApiRequest boostRequest = objectMapper.readValue(boostString, SearchApiRequest.class);
        Optional<Boost> oBoost = boostRequest.getBoosts().stream().filter(b -> b.getName().equals("location")).findFirst();
        assertTrue(oBoost.isPresent());
        Boost boost = oBoost.get();
        assertInstanceOf(GeolocationProximityBoost.class,boost);
        GeolocationProximityBoost geoBoost = (GeolocationProximityBoost) boost;
        assertEquals(new GeoLocation(new BigDecimal("25.32"),new BigDecimal("-80.93")),geoBoost.getCenter());
        assertEquals(new BigDecimal(8),geoBoost.getFactor());
        assertEquals(Boost.Function.LINEAR,geoBoost.getFunction());
        assertEquals(Boost.BoostType.PROXIMITY,geoBoost.getType());
    }

    @Test
    void boostRecency() throws JsonProcessingException{
        String boostString = TestUtil.readResourceFile("examples/requests/boostRecency.json");
        SearchApiRequest boostRequest = objectMapper.readValue(boostString, SearchApiRequest.class);
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


}
