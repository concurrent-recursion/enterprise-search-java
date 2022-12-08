package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

class SortSerializer extends StdSerializer<Sort> {
    protected SortSerializer() {
        super(Sort.class);
    }

    @Override
    public void serialize(Sort sort, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField(sort.getName(), sort.getDirection());
        jsonGenerator.writeEndObject();
    }
}
