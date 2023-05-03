package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.DeserializationUtil;
import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FacetDeserializer extends StdDeserializer<Facet> {
    protected FacetDeserializer() {
        super(Facet.class);
    }

    @Override
    public Facet deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode node = jsonParser.readValueAsTree();
        TreeNode typeNode = node.get("type");
        TextNode type = (TextNode) typeNode;
        FacetType facetType = FacetType.fromValue(type.textValue());
        TextNode nameNode = (TextNode) node.get("name");
        final ArrayNode dataArray = (ArrayNode) node.get("data");
        final String name = nameNode == null ? null : nameNode.asText();
        if(FacetType.RANGE.equals(facetType)){
            if(dataArray.isEmpty()){
                return new EmptyRangeFacet().setName(name);
            }
            final JsonNode firstValue = DeserializationUtil.getFirstValue(dataArray);
            final JsonNode firstTo = firstValue.get("to");
            final JsonNode firstFrom = firstValue.get("from");
            if((firstFrom != null && firstFrom.isNumber()) || (firstTo != null && firstTo.isNumber())){
                List<NumberRange> dataValues = new ArrayList<>();
                for(JsonNode d : dataArray) {
                    dataValues.add(jsonParser.getCodec().treeToValue(d, NumberRange.class));
                }
                return new NumberRangeFacet().setData(dataValues).setName(name);
            }else{
                List<DateRange> dateRange = new ArrayList<>();
                for(JsonNode d : dataArray){
                    dateRange.add(jsonParser.getCodec().treeToValue(d, DateRange.class));
                }
                return new DateRangeFacet().setData(dateRange).setName(name);
            }
        }else{
            final JsonNode firstValue = DeserializationUtil.getFirstValue(dataArray);
            if(dataArray.isEmpty()){
                return new EmptyValueFacet().setName(name);
            }
            TreeNode valueNode = firstValue.get("value");
            if(valueNode.asToken().isNumeric()){
                List<NumberValue> numberValues = new ArrayList<>();
                for(JsonNode d : dataArray){
                    numberValues.add(jsonParser.getCodec().treeToValue(d, NumberValue.class));
                }
                return new NumberValueFacet().setData(numberValues).setName(name);
            }else if(DeserializationUtil.isDate((TextNode) valueNode)){
                List<DateValue> dateValues = new ArrayList<>();
                for(JsonNode d : dataArray){
                    dateValues.add(jsonParser.getCodec().treeToValue(d, DateValue.class));
                }
                return new DateValueFacet().setData(dateValues).setName(name);
            }else{
                List<TextValue> textValues = new ArrayList<>();
                for(JsonNode d : dataArray){
                    textValues.add(jsonParser.getCodec().treeToValue(d, TextValue.class));
                }
                return new TextValueFacet().setData(textValues).setName(name);
            }
        }
    }

}
