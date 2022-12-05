package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

class RequestDeserializationTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    void test() throws JsonProcessingException {
        String requestExample = TestUtil.readResourceFile("examples/search-request-1.json");
        SearchApiRequest request = objectMapper.readValue(requestExample,SearchApiRequest.class);
        System.out.println(objectMapper.writeValueAsString(request));
    }
}
