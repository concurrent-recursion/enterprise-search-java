package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.client.model.response.schemas.FieldType;
import co.elasticsearch.enterprisesearch.client.model.response.schemas.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SchemaRequestSerializationTests {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void testSchema() throws JsonProcessingException {
        Schema schema = new Schema();
        schema.setField("name", FieldType.TEXT);
        schema.setField("acres",FieldType.NUMBER);
        schema.setField("location",FieldType.GEOLOCATION);
        schema.setField("date_established",FieldType.DATE);
        String json = objectMapper.writeValueAsString(schema);
        assertEquals("{\"name\":\"text\",\"acres\":\"number\",\"location\":\"geolocation\",\"date_established\":\"date\"}",json);
    }

    @Test
    void testError() throws JsonProcessingException {
        Schema errors = new Schema();
        errors.setErrors(List.of("First Error","Second Error"));
        assertEquals("{\"errors\":[\"First Error\",\"Second Error\"]}",objectMapper.writeValueAsString(errors));
    }

}
