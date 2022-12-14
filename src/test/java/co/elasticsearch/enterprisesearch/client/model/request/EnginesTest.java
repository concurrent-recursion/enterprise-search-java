package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.client.model.request.engines.CreateEngine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnginesTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void serializeCreateRequest() throws JsonProcessingException{
        CreateEngine createEngine = new CreateEngine().setName("my-large-engine").setLanguage("en").setIndexSettingOverride("number_of_shards",10);
        assertEquals("{\"name\":\"my-large-engine\",\"language\":\"en\",\"index_create_settings_override\":{\"number_of_shards\":10}}",objectMapper.writeValueAsString(createEngine));
    }


}
