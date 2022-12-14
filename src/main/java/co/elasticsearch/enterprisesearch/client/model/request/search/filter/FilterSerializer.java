package co.elasticsearch.enterprisesearch.client.model.request.search.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

class FilterSerializer extends StdSerializer<Filter> {
    protected FilterSerializer() {
        super(Filter.class);
    }

    @Override
    public void serialize(Filter filter, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        if (filter instanceof DateValueFilter) {
            DateValueFilter dvf = (DateValueFilter) filter;
            jgen.writeStartObject();
            jgen.writeObjectField(dvf.getName(), dvf.getValues());
            jgen.writeEndObject();
        } else if (filter instanceof TextValueFilter) {
            TextValueFilter tvf = (TextValueFilter) filter;
            jgen.writeStartObject();
            jgen.writeObjectField(tvf.getName(), tvf.getValues());
            jgen.writeEndObject();
        } else if (filter instanceof NumberValueFilter) {
            NumberValueFilter nvf = (NumberValueFilter) filter;
            jgen.writeStartObject();
            jgen.writeObjectField(nvf.getName(), nvf.getValues());
            jgen.writeEndObject();
        } else if (filter instanceof NumberRangeFilter) {
            NumberRangeFilter nrf = (NumberRangeFilter) filter;
            jgen.writeStartObject();
            jgen.writeObjectField(nrf.getName(), nrf.getRange());
            jgen.writeEndObject();
        } else if (filter instanceof DateRangeFilter) {
            DateRangeFilter drf = (DateRangeFilter) filter;
            jgen.writeStartObject();
            jgen.writeObjectField(drf.getName(), drf.getRange());
            jgen.writeEndObject();
        } else if (filter instanceof GeolocationFilter) {
            GeolocationFilter glf = (GeolocationFilter) filter;
            jgen.writeStartObject();
            jgen.writeObjectField(glf.getName(), glf.getRange());
            jgen.writeEndObject();
        } else if (filter instanceof BooleanFilter) {
            BooleanFilter bf = (BooleanFilter) filter;
            jgen.writeStartObject();
            if (isNotEmpty(bf.getAll())) {
                jgen.writeObjectField("all", bf.getAll());
            }
            if (isNotEmpty(bf.getAny())) {
                jgen.writeObjectField("any", bf.getAny());
            }
            if (isNotEmpty(bf.getNone())) {
                jgen.writeObjectField("none", bf.getNone());
            }
        }
    }

    private boolean isNotEmpty(List<?> list){
        return list != null && !list.isEmpty();
    }
}
