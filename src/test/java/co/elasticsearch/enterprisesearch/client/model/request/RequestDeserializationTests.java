package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.Sort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestDeserializationTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void queryOnly() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/requests/minimal.json");
        SearchApiRequest request = objectMapper.readValue(requestExample,SearchApiRequest.class);
        assertEquals("This is a test",request.getQuery());
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
}
