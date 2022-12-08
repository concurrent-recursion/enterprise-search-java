package co.elasticsearch.enterprisesearch.client.model.request.boost;

import co.elasticsearch.enterprisesearch.client.model.DeserializationUtil;
import co.elasticsearch.enterprisesearch.client.model.GeoLocation;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static co.elasticsearch.enterprisesearch.client.model.request.filter.DateValueFilter.RFC_3339;

class BoostDeserializer extends StdDeserializer<Boost> {
    protected BoostDeserializer(){
        super(Boost.class);
    }

    @Override
    public Boost deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        TreeNode node = jsonParser.readValueAsTree();


        TreeNode typeNode = node.get("type");
        if(typeNode instanceof TextNode){
            TextNode typeTextNode = (TextNode) typeNode;

            Boost.BoostType boostType = Boost.BoostType.findByValue(typeTextNode.textValue());
            if(Boost.BoostType.PROXIMITY.equals(boostType)){
                TreeNode center = node.get("center");
                TreeNode factorNode = node.get("factor");
                TreeNode functionNode = node.get("function");
                BigDecimal factor = null;
                Boost.Function function = null;
                if(factorNode instanceof NumericNode){
                    NumericNode numericFactor = (NumericNode) factorNode;
                    factor = new BigDecimal(numericFactor.asText());
                }
                if(functionNode instanceof TextNode){
                    TextNode functionText = (TextNode) functionNode;
                    function = Boost.Function.findByValue(functionText.textValue());
                }

                if(center instanceof TextNode){
                    TextNode centerText = (TextNode) center;
                    if("now".equals(centerText.asText()) || DeserializationUtil.isDate(centerText)){
                        RecencyBoost recencyBoost = new RecencyBoost().setFactor(factor).setFunction(function);
                        if("now".equals(centerText.asText())){
                            recencyBoost.setUseNow(true);
                        }else {
                            recencyBoost.setCenter(OffsetDateTime.parse(centerText.asText(), RFC_3339));
                        }
                        return recencyBoost;
                    }else{
                        GeoLocation geoCenter = jsonParser.getCodec().treeToValue(center,GeoLocation.class);
                        return new GeolocationProximityBoost().setFactor(factor).setFunction(function).setCenter(geoCenter);
                    }
                }else if(center instanceof NumericNode){
                    NumericNode centerNumber = (NumericNode) center;
                    return new NumberProximityBoost().setFactor(factor).setFunction(function)
                            .setCenter(new BigDecimal(centerNumber.asText()));
                }
            } else if (Boost.BoostType.FUNCTIONAL.equals(boostType)) {
                return new FunctionalBoost().setFactor(getFactor(node)).setFunction(getFunction(node)).setOperation(getOperation(node));
            } else if (Boost.BoostType.VALUE.equals(boostType)) {
                TreeNode valueNode = node.get("value");
                final JsonNode firstValue = DeserializationUtil.getFirstValue(valueNode);
                final Stream<JsonNode> nodeStream = DeserializationUtil.getNodeStream(valueNode);
                if(firstValue instanceof TextNode){
                    if(DeserializationUtil.isDate((TextNode) firstValue)){
                        return new DateValueBoost().setFactor(getFactor(node)).setOperation(getOperation(node)).setValue(nodeStream.map(JsonNode::asText).map(dateVal -> OffsetDateTime.parse(dateVal,RFC_3339)).collect(Collectors.toList()));
                    }else {
                        return new TextValueBoost().setFactor(getFactor(node)).setOperation(getOperation(node)).setValue(nodeStream.map(JsonNode::asText).collect(Collectors.toList()));
                    }
                } else if (firstValue instanceof NumericNode) {

                    return new NumericValueBoost().setFactor(getFactor(node)).setOperation(getOperation(node)).setValue(nodeStream.map(numVal -> new BigDecimal(numVal.asText())).collect(Collectors.toList()));
                }
            }
        }
        return null;
    }

    private BigDecimal getFactor(TreeNode node){
        TreeNode factorNode = node.get("factor");
        if(factorNode instanceof NumericNode){
            NumericNode numericFactor = (NumericNode) factorNode;
            return new BigDecimal(numericFactor.asText());
        }
        return null;
    }
    private Boost.Function getFunction(TreeNode node){
        TreeNode functionNode = node.get("function");
        if(functionNode instanceof TextNode){
            TextNode functionText = (TextNode) functionNode;
            return Boost.Function.findByValue(functionText.textValue());
        }
        return null;
    }
    private Boost.Operation getOperation(TreeNode node){
        TreeNode operationNode = node.get("operation");
        if(operationNode instanceof TextNode){
            TextNode operationText = (TextNode) operationNode;
            return Boost.Operation.findByValue(operationText.textValue());
        }
        return null;
    }


}
