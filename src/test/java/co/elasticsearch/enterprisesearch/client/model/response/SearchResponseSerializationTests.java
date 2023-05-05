package co.elasticsearch.enterprisesearch.client.model.response;

import co.elasticsearch.enterprisesearch.client.model.Engine;
import co.elasticsearch.enterprisesearch.client.model.request.search.SearchRequest;
import co.elasticsearch.enterprisesearch.client.model.response.search.Meta;
import co.elasticsearch.enterprisesearch.client.model.response.search.SearchApiResponse;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.Facet;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.TextValueFacet;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.TextValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class SearchResponseSerializationTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
    }

    @SneakyThrows
    String writeValueAsString(Object o){
        return objectMapper.writeValueAsString(o);
    }

    @Test
    void serializeSimpleResponse(){
        SearchApiResponse<NationalParkDocument> response = new SearchApiResponse<>();
        ResponsePage page = new ResponsePage().setTotalPages(0).setTotalResults(0);
        page.setCurrent(1).setSize(10);
        Meta meta = new Meta().setPrecision(3).setRequestId("ABC123").setPage(page).setEngine(new Engine().setName("TestEngine"));
        response.setMeta(meta);

        Assertions.assertEquals("{\"meta\":{" +
                "\"alerts\":[]," +
                "\"warnings\":[]," +
                "\"precision\":3," +
                "\"page\":{" +
                    "\"total_pages\":0," +
                    "\"size\":10," +
                    "\"current\":1," +
                    "\"total_results\":0" +
                "}," +
                "\"engine\":{\"name\":\"TestEngine\",\"type\":\"default\"}," +
                "\"request_id\":\"ABC123\"" +
                "},\"results\":[]}",writeValueAsString(response));

    }

    @Test
    void serializeFacets(){

        SearchApiResponse<NationalParkDocument> response = new SearchApiResponse<>();
        ResponsePage page = new ResponsePage().setTotalPages(0).setTotalResults(0);
        page.setCurrent(1).setSize(10);
        Meta meta = new Meta().setPrecision(3).setRequestId("ABC123").setPage(page).setEngine(new Engine().setName("TestEngine"));
        response.setMeta(meta);
        List<Facet> responseFacets = new ArrayList<>();
        TextValueFacet facet = new TextValueFacet().setName("top-five-states");
        facet.getData().add(new TextValue().setValue("California").setCount(8L));
        facet.getData().add(new TextValue().setValue("Alaska").setCount(5L));
        responseFacets.add(facet);

        response.setFacetMap(Map.of("states",responseFacets));

        Assertions.assertEquals("{" +
                "\"meta\":{" +
                    "\"alerts\":[]," +
                    "\"warnings\":[]," +
                    "\"precision\":3," +
                    "\"page\":{" +
                        "\"total_pages\":0," +
                        "\"size\":10," +
                        "\"current\":1," +
                        "\"total_results\":0" +
                    "}," +
                    "\"engine\":{\"name\":\"TestEngine\",\"type\":\"default\"}," +
                    "\"request_id\":\"ABC123\"" +
                "}," +
                "\"results\":[]," +
                "\"facets\":{" +
                    "\"states\":[" +
                        "{" +
                            "\"type\":\"value\"," +
                            "\"name\":\"top-five-states\"," +
                            "\"data\":[" +
                                "{" +
                                "\"value\":\"California\"," +
                                "\"count\":8" +
                                "}," +
                                "{" +
                                "\"value\":\"Alaska\"," +
                                "\"count\":5" +
                                "}" +
                            "]" +
                        "}" +
                    "]" +
                "}}",writeValueAsString(response));

    }

    @Test
    void serializeQuery() {
        SearchRequest request = new SearchRequest();
        request.setQuery("This is a test");
        String json = writeValueAsString(request);
        Assertions.assertEquals("{\"query\":\"This is a test\"}",json);
    }
}
