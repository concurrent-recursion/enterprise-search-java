package co.elasticsearch.enterprisesearch.client.model.request.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
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
        FacetType facetType = FacetType.fromValue(type.asText());
        if (FacetType.RANGE.equals(facetType)) {
            return jsonParser.getCodec().treeToValue(node, SearchRangeFacet.class);
        } else {
            return jsonParser.getCodec().treeToValue(node, ValueFacet.class);
        }

    }
}
