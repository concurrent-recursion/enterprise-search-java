package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.response.schemas.FieldType;
import co.elasticsearch.enterprisesearch.client.model.response.schemas.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static co.elasticsearch.enterprisesearch.client.model.response.schemas.FieldType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SchemaTests {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void schemaSerialize() throws JsonProcessingException {
        Schema schema = new Schema();
        schema.setField("name", TEXT);
        schema.setField("acres",FieldType.NUMBER);
        schema.setField("location",FieldType.GEOLOCATION);
        schema.setField("date_established",FieldType.DATE);
        String json = objectMapper.writeValueAsString(schema);
        assertEquals("{\"name\":\"text\",\"acres\":\"number\",\"location\":\"geolocation\",\"date_established\":\"date\"}",json);
    }

    @Test
    void schemaDeserialize() throws JsonProcessingException{
        String json = TestUtil.readResourceFile("examples/responses/schema.json");
        Schema jsonSchema = objectMapper.readValue(json,Schema.class);
        Schema schema = new Schema().setField("description", TEXT).setField("nps_link", TEXT)
            .setField("states", TEXT).setField("title", TEXT).setField("visitors",NUMBER)
            .setField("world_heritage_site", TEXT).setField("location",GEOLOCATION)
            .setField("acres",NUMBER).setField("date_established",DATE).setField("square_km",NUMBER)
        ;
        assertEquals(schema,jsonSchema);
    }

    @Test
    void schemaDeserializeError() throws JsonProcessingException {
        Schema errors = new Schema();
        errors.setErrors(List.of("First Error","Second Error"));
        assertEquals("{\"errors\":[\"First Error\",\"Second Error\"]}",objectMapper.writeValueAsString(errors));
    }



}
