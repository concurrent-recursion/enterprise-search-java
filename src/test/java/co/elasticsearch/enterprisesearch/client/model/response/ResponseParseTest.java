package co.elasticsearch.enterprisesearch.client.model.response;

import co.elasticsearch.enterprisesearch.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
    }
}
