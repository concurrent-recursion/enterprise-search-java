package co.elasticsearch.enterprisesearch;

import co.elasticsearch.enterprisesearch.client.model.*;
import co.elasticsearch.enterprisesearch.client.model.request.*;
import co.elasticsearch.enterprisesearch.client.model.request.facet.Facet;
import co.elasticsearch.enterprisesearch.client.model.request.facet.SearchRangeFacet;
import co.elasticsearch.enterprisesearch.client.model.request.facet.ValueFacet;
import co.elasticsearch.enterprisesearch.client.model.request.range.DateRange;
import co.elasticsearch.enterprisesearch.client.model.request.range.NumberRange;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.util.*;

@Slf4j
class EnterpriseSearchJavaApplicationTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setup(){
        objectMapper.registerModule(new JavaTimeModule());

    }

    @SneakyThrows
    String writeValueAsString(Object o){
        return objectMapper.writeValueAsString(o);
    }

    @Test
    void serializeEmptyRequest(){
        SearchApiRequest request = new SearchApiRequest();
        Assertions.assertEquals("{}",writeValueAsString(request));
    }

    @Test
    void serializeQuery() {
        SearchApiRequest request = new SearchApiRequest();
        request.setQuery("This is a test");
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"This is a test\"}",json);
    }

    @Test
    void serializePage() {
        SearchApiRequest defaultPageSize = new SearchApiRequest().setPage(new Page().setCurrent(1).setSize(10));
        String json = writeValueAsString(defaultPageSize);
        Assertions.assertEquals("{}",json);

        SearchApiRequest request = new SearchApiRequest().setPage(new Page().setCurrent(2).setSize(30));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"page\":{\"size\":30,\"current\":2}}",json);
    }

    @Test
    void serializeSort(){
        SearchApiRequest defaultPageSize = new SearchApiRequest().setSort(List.of(new Sort(Sort.SCORE,Sort.Direction.ASCENDING)));
        String json = writeValueAsString(defaultPageSize);
        Assertions.assertEquals("{\"sort\":[{\"_score\":\"asc\"}]}",json);

        SearchApiRequest multiple = new SearchApiRequest().setSort(List.of(new Sort("pubdate",Sort.Direction.DESCENDING),new Sort(Sort.SCORE, Sort.Direction.ASCENDING)));
        json = writeValueAsString(multiple);
        Assertions.assertEquals("{\"sort\":[{\"pubdate\":\"desc\"},{\"_score\":\"asc\"}]}",json);
    }

    @Test
    void serializeGroup(){
        SearchApiRequest request = new SearchApiRequest().setGroup(
                new Group()
                        .setSort(new Sort(Sort.SCORE,Sort.Direction.ASCENDING))
                        .setField("states")
                        .setCollapse(true)
                        .setSize(20)
        );
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"group\":{\"field\":\"states\",\"size\":20,\"sort\":{\"_score\":\"asc\"},\"collapse\":true}}",json);

        SearchApiRequest request1 = new SearchApiRequest().setGroup(new Group().setField("states"));
        json = writeValueAsString(request1);
        Assertions.assertEquals("{\"group\":{\"field\":\"states\"}}",json);
    }

    @Test
    void serializeFacets(){
        final Map<String,List<Facet>> facets = new HashMap<>();
        SearchApiRequest request = new SearchApiRequest().setFacets(facets);
        String json = writeValueAsString(request);
        Assertions.assertEquals("{}",json);

        //Value
        request = new SearchApiRequest();
        request.getFacets().put("states",List.of(new ValueFacet().setName("top-five-states").setSort(new Sort("count",Sort.Direction.DESCENDING)).setSize(5)));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"facets\":{\"states\":[{\"type\":\"value\",\"name\":\"top-five-states\",\"sort\":{\"count\":\"desc\"},\"size\":5}]}}",json);

        //Range on Number
        request = new SearchApiRequest();
        request.getFacets().put("acres",List.of(new SearchRangeFacet().setName("min-and-max-range").setRanges(List.of(
                new NumberRange().setFrom(new BigDecimal("1")).setTo(new BigDecimal("10000")),
                new NumberRange().setFrom(new BigDecimal("10000"))
        ))));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"facets\":{\"acres\":[{\"type\":\"range\",\"name\":\"min-and-max-range\",\"ranges\":[{\"from\":1,\"to\":10000},{\"from\":10000}]}]}}",json);

        //Range on a Date
        request = new SearchApiRequest();

        request.getFacets().put("date_established",List.of(new SearchRangeFacet().setName("half-century").setRanges(List.of(
               new DateRange().setFrom(OffsetDateTime.parse("1900-01-01T12:00:00Z")).setTo(OffsetDateTime.parse("1950-01-01T00:00:00.11+00:00"))
        ))));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"facets\":{\"date_established\":[{\"type\":\"range\",\"name\":\"half-century\",\"ranges\":[{\"from\":\"1900-01-01T12:00:00.00Z\",\"to\":\"1950-01-01T00:00:00.11Z\"}]}]}}",json);

        //Range on Geolocation
        request = new SearchApiRequest();
        request.getFacets().put("location",List.of(new SearchRangeFacet().setName("geo-range-from-san-francisco").setCenter(new GeoLocation(new BigDecimal("37.386483"),new BigDecimal("-122.083842"))).setUnit(GeoLocation.Unit.METERS).setRanges(List.of(
                new NumberRange().setFrom(BigDecimal.ZERO).setTo(new BigDecimal("100000")).setName("Nearby"),
                new NumberRange().setFrom(new BigDecimal("100000")).setTo(new BigDecimal("300000")).setName("A longer drive."),
                new NumberRange().setFrom(new BigDecimal("300000")).setName("Perhaps fly?")

        ))));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"facets\":{\"location\":[{\"type\":\"range\",\"name\":\"geo-range-from-san-francisco\",\"center\":[-122.083842,37.386483],\"unit\":\"m\",\"ranges\":[{\"from\":0,\"to\":100000,\"name\":\"Nearby\"},{\"from\":100000,\"to\":300000,\"name\":\"A longer drive.\"},{\"from\":300000,\"name\":\"Perhaps fly?\"}]}]}}",json);

    }

    @Test
    void serializePrecision(){
        SearchApiRequest request = new SearchApiRequest().setPrecision(1);
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"precision\":1}",json);
    }

    @Test
    void serializeSearchFields(){
        SearchApiRequest request = new SearchApiRequest();
        request.getSearchFields().put("title",new SearchField());
        request.getSearchFields().put("description",new SearchField());
        request.getSearchFields().put("states",new SearchField());
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"search_fields\":{\"title\":{},\"description\":{},\"states\":{}}}",json);

        request = new SearchApiRequest();
        request.getSearchFields().put("title",new SearchField().setWeight(10));
        request.getSearchFields().put("description",new SearchField().setWeight(5));
        request.getSearchFields().put("states",new SearchField().setWeight(3));
        json = writeValueAsString(request);
        Assertions.assertEquals("{\"search_fields\":{\"title\":{\"weight\":10},\"description\":{\"weight\":5},\"states\":{\"weight\":3}}}",json);
    }

    @Test
    void serializeResultsFields(){
        SearchApiRequest request = new SearchApiRequest();
        request.getResultFields().put("title",new ResultField().setRaw(new ResultFieldRendered()));
        request.getResultFields().put("description",new ResultField().setRaw(new ResultFieldRendered().setSize(50)));
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"result_fields\":{\"title\":{\"raw\":{}},\"description\":{\"raw\":{\"size\":50}}}}",json);

        SearchApiRequest request2 = new SearchApiRequest();
        request2.getResultFields().put("title",new ResultField().setSnippet(new ResultFieldRendered().setSize(20).setFallback(true)));
        request2.getResultFields().put("description",new ResultField().setRaw(new ResultFieldRendered().setSize(200)).setSnippet(new ResultFieldRendered().setSize(100)));
        request2.getResultFields().put("states",new ResultField().setRaw(new ResultFieldRendered()).setSnippet(new ResultFieldRendered().setSize(20).setFallback(true)));
        String json2 = writeValueAsString(request2);
        Assertions.assertEquals("{\"result_fields\":{\"title\":{\"snippet\":{\"size\":20,\"fallback\":true}},\"description\":{\"raw\":{\"size\":200},\"snippet\":{\"size\":100}},\"states\":{\"raw\":{},\"snippet\":{\"size\":20,\"fallback\":true}}}}",json2);
    }

    @Test
    void serializeAnalytics(){
        SearchApiRequest request = new SearchApiRequest();
        request.getAnalytics().addTag("web").addTag("mobile");
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"analytics\":{\"tags\":[\"web\",\"mobile\"]}}",json);
    }

    @Test
    void serializeRecordAnalytics(){
        SearchApiRequest request = new SearchApiRequest();
        request.setRecordAnalytics(true);
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"record_analytics\":true}",json);
    }


    @Test
    @Disabled
    void JsonDeserializeTest() throws Exception{
        Path path = Paths.get("src/test/resources/examples/search-request-1.json");
        try(InputStream is = Files.newInputStream(path, StandardOpenOption.READ)){
            SearchApiRequest request = objectMapper.readValue(is,SearchApiRequest.class);
            Assertions.assertNotNull(request,"SearchApiRequest should not be null");
        }
    }

}
