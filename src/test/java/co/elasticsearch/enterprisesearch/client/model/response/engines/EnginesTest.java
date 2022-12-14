package co.elasticsearch.enterprisesearch.client.model.response.engines;

import co.elasticsearch.enterprisesearch.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnginesTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void deserializeResponse() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/enginescreate.json");
        EngineResponse jsonResponse = objectMapper.readValue(json,EngineResponse.class);
        EngineResponse response = new EngineResponse().setName("my-large-engine").setDocumentCount(0L).setType("default").setIndexCreateSettingsOverride(Map.of("number_of_shards",10));
        assertEquals(response,jsonResponse);
    }

    @Test
    void deserializeError() throws JsonProcessingException {
        String json = "{\"errors\":[\"Name is already taken\"]}";
        EngineResponse error = objectMapper.readValue(json, EngineResponse.class);
        assertTrue(error.isError());
        assertEquals(1,error.getErrors().size());
    }
}
