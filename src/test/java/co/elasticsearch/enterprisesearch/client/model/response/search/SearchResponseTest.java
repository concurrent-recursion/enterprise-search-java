package co.elasticsearch.enterprisesearch.client.model.response.search;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.response.TestSearchDocument;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSearchResponse() throws Exception {
        String json = TestUtil.readResourceFile("examples/responses/search-response-1.json");
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, TestSearchDocument.class);
        SearchApiResponse<TestSearchDocument> jsonResponse = objectMapper.readValue(json,type);
        assertEquals(100,jsonResponse.getResults().size());
    }
}
