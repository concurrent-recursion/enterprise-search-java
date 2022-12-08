package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.math.BigDecimal;

class GeolocationDeserializer extends StdDeserializer<GeoLocation> {
    protected GeolocationDeserializer() {
        super(GeoLocation.class);
    }

    @Override
    public GeoLocation deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        TreeNode node = jsonParser.readValueAsTree();
        if (node.isArray()) {
            ArrayNode geoArray = (ArrayNode) node;
            return new GeoLocation(new BigDecimal(geoArray.get(1).asText()), new BigDecimal(geoArray.get(0).asText()));
        } else {
            TextNode geoText = (TextNode) node;
            return new GeoLocation(geoText.asText());
        }

    }
}
