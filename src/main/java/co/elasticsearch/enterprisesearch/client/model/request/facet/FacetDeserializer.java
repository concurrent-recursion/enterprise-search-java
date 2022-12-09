package co.elasticsearch.enterprisesearch.client.model.request.facet;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;

class FacetDeserializer extends StdDeserializer<Facet> {
    protected FacetDeserializer() {
        super(Facet.class);
    }

    @Override
    public Facet deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode node = jsonParser.readValueAsTree();
        TextNode type = (TextNode) node.get("type");
        if ("range".equals(type.asText())) {
            return jsonParser.getCodec().treeToValue(node, SearchRangeFacet.class);
        } else {
            return jsonParser.getCodec().treeToValue(node, ValueFacet.class);
        }

    }
}
