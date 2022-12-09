package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

class ResultFieldSerializer extends StdSerializer<ResultField> {
    protected ResultFieldSerializer() {
        super(ResultField.class);
    }

    @Override
    public void serialize(ResultField resultField, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        if (resultField.getRaw() != null) {
            jgen.writeObjectField("raw", resultField.getRaw());
        }
        if (resultField.getSnippet() != null) {
            jgen.writeObjectField("snippet", resultField.getSnippet());
        }
        jgen.writeEndObject();
    }
}
