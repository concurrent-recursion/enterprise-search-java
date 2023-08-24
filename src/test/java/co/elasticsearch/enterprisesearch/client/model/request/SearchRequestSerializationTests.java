package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.client.model.Geolocation;
import co.elasticsearch.enterprisesearch.client.model.GeolocationRange;
import co.elasticsearch.enterprisesearch.client.model.Sort;
import co.elasticsearch.enterprisesearch.client.model.request.search.*;
import co.elasticsearch.enterprisesearch.client.model.request.search.boost.*;
import co.elasticsearch.enterprisesearch.client.model.request.search.facet.FacetSortField;
import co.elasticsearch.enterprisesearch.client.model.request.search.facet.SearchRangeFacet;
import co.elasticsearch.enterprisesearch.client.model.request.search.facet.ValueFacet;
import co.elasticsearch.enterprisesearch.client.model.request.search.filter.*;
import co.elasticsearch.enterprisesearch.client.model.request.search.range.DateRange;
import co.elasticsearch.enterprisesearch.client.model.request.search.range.NumberRange;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

class SearchRequestSerializationTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
    }

    @SneakyThrows
    String writeValueAsString(Object o){
        return objectMapper.writeValueAsString(o);
    }

    @Test
    void serializeEmptyRequest(){
        SearchRequest request = new SearchRequest();
        Assertions.assertEquals("{\"query\":\"\"}",writeValueAsString(request));
    }

    @Test
    void serializeQuery() {
        SearchRequest request = new SearchRequest();
        request.setQuery("This is a test");
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"This is a test\"}",json);
    }

    @Test
    void serializePage() {
        SearchRequest defaultPageSize = new SearchRequest().setPage(new Page().setCurrent(1).setSize(10));
        String json = writeValueAsString(defaultPageSize);
        Assertions.assertEquals("{\"query\":\"\"}",json);

        SearchRequest request = new SearchRequest().setPage(new Page().setCurrent(2).setSize(30));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"page\":{\"size\":30,\"current\":2}}",json);
    }

    @Test
    void serializeSort(){
        SearchRequest defaultPageSize = new SearchRequest().withSorts(new Sort(Sort.SCORE, Sort.Order.ASCENDING));
        String json = writeValueAsString(defaultPageSize);
        Assertions.assertEquals("{\"query\":\"\",\"sort\":{\"_score\":\"asc\"}}",json);

        SearchRequest multiple = new SearchRequest().withSorts(new Sort("pubdate", Sort.Order.DESCENDING),new Sort(Sort.SCORE, Sort.Order.ASCENDING));
        json = writeValueAsString(multiple);
        Assertions.assertEquals("{\"query\":\"\",\"sort\":[{\"pubdate\":\"desc\"},{\"_score\":\"asc\"}]}",json);


        SearchRequest geoSort = new SearchRequest();
        List<Sort> sorts = geoSort.getSort();
        sorts.add(new Sort("location", new GeolocationSort(new Geolocation("-77.08","38.89")).setMode(GeolocationSort.Mode.MIN).setOrder(Sort.Order.ASCENDING)));
        sorts.add(new Sort("title", Sort.Order.ASCENDING));
        json = writeValueAsString(geoSort);
        Assertions.assertEquals("{\"query\":\"\",\"sort\":[{\"location\":{\"center\":[38.89,-77.08],\"mode\":\"min\",\"order\":\"asc\"}},{\"title\":\"asc\"}]}",json);

    }

    @Test
    void serializeFilters(){
        SearchRequest request = new SearchRequest();

        request.setFilters(new TextValueFilter("world_heritage_site").setValues(List.of("true")));
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"filters\":{\"world_heritage_site\":\"true\"}}",json);

        request = new SearchRequest();
        request.setFilters(new NumberValueFilter("acres",20,30));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"filters\":{\"acres\":[20,30]}}",json);

        request = new SearchRequest();
        request.setFilters(new DateValueFilter("date_established", OffsetDateTime.parse("1900-01-01T12:00:00.00Z",DateValueFilter.RFC_3339)));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"filters\":{\"date_established\":\"1900-01-01T12:00:00Z\"}}",json);

        request = new SearchRequest();
        request.setFilters(new NumberRangeFilter("acres").setRange(new NumberRange(30,300)));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"filters\":{\"acres\":{\"from\":30,\"to\":300}}}",json);

        request = new SearchRequest();
        request.setFilters(new DateRangeFilter("date_established").setRange(new DateRange().setFrom(OffsetDateTime.parse("1900-01-01T12:00:00+00:00")).setTo(OffsetDateTime.parse("1950-01-01T00:00:00+00:00"))));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"filters\":{\"date_established\":{\"from\":\"1900-01-01T12:00:00.00Z\",\"to\":\"1950-01-01T00:00:00.00Z\"}}}",json);

        request = new SearchRequest();
        request.setFilters(new GeolocationFilter("location").setRange(new GeolocationRange().setCenter(new Geolocation("37.386483,-122.083842")).setDistance(new BigDecimal(300)).setUnit(Geolocation.Unit.KILOMETERS)));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"filters\":{\"location\":{\"center\":[-122.083842,37.386483],\"distance\":300,\"unit\":\"km\"}}}",json);

        request = new SearchRequest();
        request.setFilters(new GeolocationFilter("location").setRange(new GeolocationRange().setCenter(new Geolocation("37.386483,-122.083842")).setFrom(new BigDecimal(0)).setTo(new BigDecimal(1000)).setUnit(Geolocation.Unit.METERS)));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"filters\":{\"location\":{\"center\":[-122.083842,37.386483],\"unit\":\"m\",\"from\":0,\"to\":1000}}}",json);


        request = new SearchRequest();
        request.setFilters(new BooleanFilter().allOf());
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"filters\":{}}",json);

        request = new SearchRequest();
        request.setFilters(new BooleanFilter()
                .allOf(
                        new TextValueFilter("states").setValues(List.of("California")),
                        new TextValueFilter("world_heritage_site").setValues(List.of("true"))
                ).anyOf(
                        new NumberRangeFilter("acres").setRange(new NumberRange(40_000,null)),
                        new NumberRangeFilter("square_km").setRange(new NumberRange(500,null))
                ).noneOf(
                        new TextValueFilter("title").setValues(List.of("Yosemite"))
                )
        );
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"filters\":{\"all\":[{\"states\":\"California\"},{\"world_heritage_site\":\"true\"}],\"any\":[{\"acres\":{\"from\":40000}},{\"square_km\":{\"from\":500}}],\"none\":{\"title\":\"Yosemite\"}}}",json);
    }

    @Test
    void serializeGroup(){
        SearchRequest request = new SearchRequest().setGroup(
                new Group("states").setSort(new Sort(Sort.SCORE,Sort.Order.ASCENDING))
                        .setCollapse(true)
                        .setSize(20)
        );
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"group\":{\"field\":\"states\",\"size\":20,\"sort\":{\"_score\":\"asc\"},\"collapse\":true}}",json);

        SearchRequest request1 = new SearchRequest().setGroup(new Group("states"));
        json = writeValueAsString(request1);
        Assertions.assertEquals("{\"query\":\"\",\"group\":{\"field\":\"states\"}}",json);
    }

    @Test
    void serializeFacets(){


        //Value
        SearchRequest request = new SearchRequest();
        request.withFacets(new ValueFacet("states").setName("top-five-states").setSortField(FacetSortField.COUNT).setSortOrder(Sort.Order.DESCENDING).setSize(5));
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"facets\":{\"states\":{\"type\":\"value\",\"name\":\"top-five-states\",\"sort\":{\"count\":\"desc\"},\"size\":5}}}",json);

        //Range on Number
        request = new SearchRequest();
        request.withFacets(new SearchRangeFacet("acres").setName("min-and-max-range").setRanges(List.of(
                new NumberRange("1","10000"),
                new NumberRange(10000,null)
        )));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"facets\":{\"acres\":{\"type\":\"range\",\"name\":\"min-and-max-range\",\"ranges\":[{\"from\":1,\"to\":10000},{\"from\":10000}]}}}",json);

        //Range on a Date
        request = new SearchRequest();

        request.withFacets(new SearchRangeFacet("date_established").setName("half-century").setRanges(List.of(
                new DateRange().setFrom(OffsetDateTime.parse("1900-01-01T12:00:00Z")).setTo(OffsetDateTime.parse("1950-01-01T00:00:00.11+00:00"))
        )));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"facets\":{\"date_established\":{\"type\":\"range\",\"name\":\"half-century\",\"ranges\":{\"from\":\"1900-01-01T12:00:00.00Z\",\"to\":\"1950-01-01T00:00:00.11Z\"}}}}",json);

        //Range on Geolocation
        request = new SearchRequest();
        request.withFacets(new SearchRangeFacet("location").setName("geo-range-from-san-francisco").setCenter(new Geolocation(new BigDecimal("37.386483"),new BigDecimal("-122.083842"))).setUnit(Geolocation.Unit.METERS).setRanges(List.of(
                new NumberRange().setFrom(BigDecimal.ZERO).setTo(new BigDecimal("100000")).setName("Nearby"),
                new NumberRange().setFrom(new BigDecimal("100000")).setTo(new BigDecimal("300000")).setName("A longer drive."),
                new NumberRange().setFrom(new BigDecimal("300000")).setName("Perhaps fly?")

        )));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"facets\":{\"location\":{\"type\":\"range\",\"name\":\"geo-range-from-san-francisco\",\"center\":[-122.083842,37.386483],\"unit\":\"m\",\"ranges\":[{\"from\":0,\"to\":100000,\"name\":\"Nearby\"},{\"from\":100000,\"to\":300000,\"name\":\"A longer drive.\"},{\"from\":300000,\"name\":\"Perhaps fly?\"}]}}}",json);

    }



    @Test
    void serializePrecision(){
        SearchRequest request = new SearchRequest().setPrecision(1);
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"precision\":1}",json);
    }

    @Test
    void stringValueBoost() {
        SearchRequest request = new SearchRequest();
        request.withBoosts(new TextValueBoost().setName("world_heritage_site").setOperation(Boost.Operation.MULTIPLY).setValue(List.of("true")).setFactor(new BigDecimal(10)));
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"boosts\":{\"world_heritage_site\":{\"type\":\"value\",\"value\":\"true\",\"operation\":\"multiply\",\"factor\":10}}}", json);
    }
    @Test
    void functionalBoost() {
        SearchRequest request = new SearchRequest();
        request.withBoosts(new FunctionalBoost().setName("visitors").setFunction(Boost.Function.LOGARITHMIC).setOperation(Boost.Operation.MULTIPLY).setFactor(new BigDecimal(2)));
        String functionalJson = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"boosts\":{\"visitors\":{\"type\":\"functional\",\"function\":\"logarithmic\",\"operation\":\"multiply\",\"factor\":2}}}", functionalJson);

    }
    @Test
    void geolocationBoost() {
        SearchRequest request = new SearchRequest();
        Boost geoBoost = new GeolocationProximityBoost()
                .setName("location")
                .setFactor(new BigDecimal(8))
                .setCenter(new Geolocation("-80.93","25.32"))
                .setFunction(Boost.Function.LINEAR);
        request.withBoosts(geoBoost);
        String proximityJson = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"boosts\":{\"location\":{\"type\":\"proximity\",\"function\":\"linear\",\"center\":[25.32,-80.93],\"factor\":8}}}", proximityJson);
    }
    @Test
    void functionalLinearBoost() {
        SearchRequest request = new SearchRequest();
        Boost functionalBoost = new NumberProximityBoost().setName("acres").setFunction(Boost.Function.LINEAR).setCenter(new BigDecimal("205.2")).setFactor(new BigDecimal(8));
        request.withBoosts(functionalBoost);
        String numberProximityJson = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"boosts\":{\"acres\":{\"type\":\"proximity\",\"function\":\"linear\",\"center\":205.2,\"factor\":8}}}", numberProximityJson);
    }

    @Test
    void recencyBoost(){
        SearchRequest request = new SearchRequest();
        Boost recencyBoost = new RecencyBoost().setName("date_established").setFunction(Boost.Function.LINEAR).setUseNow(true).setFactor(new BigDecimal(8));
        request.withBoosts(recencyBoost);
        String recencyJson = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"boosts\":{\"date_established\":{\"type\":\"proximity\",\"function\":\"linear\",\"center\":\"now\",\"factor\":8}}}",recencyJson);

    }

    @Test
    void serializeSearchFields(){
        SearchRequest request = new SearchRequest()
                .withSearchFields(new SearchField("title"),new SearchField("description"),new SearchField("states"));
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"search_fields\":{\"title\":{},\"description\":{},\"states\":{}}}",json);

        request = new SearchRequest().withSearchFields(new SearchField("title").setWeight(new BigDecimal("10")),new SearchField("description").setWeight(new BigDecimal("5")),new SearchField("states").setWeight(new BigDecimal("3")));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"search_fields\":{\"title\":{\"weight\":10},\"description\":{\"weight\":5},\"states\":{\"weight\":3}}}",json);
    }

    @Test
    void serializeResultsFields(){
        SearchRequest request = new SearchRequest()
                .withResultFields(new ResultField("title").withRaw(),new ResultField("description").withRaw(50));
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"result_fields\":{\"title\":{\"raw\":{}},\"description\":{\"raw\":{\"size\":50}}}}",json);

        SearchRequest request2 = new SearchRequest().withResultFields(
                new ResultField("title").withSnippet(20,true),
            new ResultField("description").withRaw(200).withSnippet(100),
                new ResultField("states").withRaw().withSnippet(20,true)
        );
        String json2 = writeValueAsString(request2);
        Assertions.assertEquals("{\"query\":\"\",\"result_fields\":{\"title\":{\"snippet\":{\"size\":20,\"fallback\":true}},\"description\":{\"raw\":{\"size\":200},\"snippet\":{\"size\":100}},\"states\":{\"raw\":{},\"snippet\":{\"size\":20,\"fallback\":true}}}}",json2);
    }

    @Test
    void serializeAnalytics(){
        SearchRequest request = new SearchRequest();
        request.getAnalytics().addTag("web").addTag("mobile");
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"analytics\":{\"tags\":[\"web\",\"mobile\"]}}",json);
    }

    @Test
    void serializeRecordAnalytics(){
        SearchRequest request = new SearchRequest();
        request.setRecordAnalytics(false);
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"\",\"record_analytics\":false}",json);
    }



}
