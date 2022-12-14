package co.elasticsearch.enterprisesearch.client.model.response;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResult;
import co.elasticsearch.enterprisesearch.client.model.response.search.SearchApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class ResponseParseTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.registerModule(new JavaTimeModule());
    }



    @Test
    void deserializeResponse() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/parks-1.json");
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, NationalParkDocument.class);
        SearchApiResponse<NationalParkDocument> response = objectMapper.readValue(json, type);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
    }

    @Test
    void indexResponse() throws JsonProcessingException{
        String json = TestUtil.readResourceFile("examples/responses/index.json");
        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, IndexResult.class);
        List<IndexResult> results = objectMapper.readValue(json,listType);
        IndexResponse response = new IndexResponse().setDocuments(results);
        assertEquals(3,response.getDocuments().size());
        assertTrue(response.isError());
    }
}
