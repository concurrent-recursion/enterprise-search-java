package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.TestUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

public class RequestDeserializationTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void test() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/search-request-1.json");
        SearchApiRequest request = objectMapper.readValue(requestExample,SearchApiRequest.class);
    }
}
