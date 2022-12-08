package co.elasticsearch.enterprisesearch.client.model.request.facet;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;

class FacetDeserializer extends StdDeserializer<Facet> {
    protected FacetDeserializer(){
        super(Facet.class);
    }
    @Override
    public Facet deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        TreeNode node = jsonParser.readValueAsTree();
        TextNode type = (TextNode) node.get("type");
        if("range".equals(type.asText())){
           return jsonParser.getCodec().treeToValue(node,SearchRangeFacet.class);
           /* SearchRangeFacet rangeFacet = new SearchRangeFacet();
            rangeFacet.setName(((TextNode)node.get("name")).asText());
            if(node.get("center") != null){
                TreeNode center = node.get("center");
                GeoLocation location = jsonParser.getCodec().treeToValue(center, GeoLocation.class);
                rangeFacet.setCenter(location);
                if(node.get("unit") != null){
                    rangeFacet.setUnit(jsonParser.getCodec().treeToValue(node.get("unit"),GeoLocation.Unit.class));
                }
            }
            ArrayNode ranges = (ArrayNode) node.get("ranges");
            ranges.forEach(rn -> {
                try {
                    rangeFacet.getRanges().add(jsonParser.getCodec().treeToValue(rn, Range.class));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
            return rangeFacet;*/
        }else{
            return jsonParser.getCodec().treeToValue(node, ValueFacet.class);
        }

    }
}
