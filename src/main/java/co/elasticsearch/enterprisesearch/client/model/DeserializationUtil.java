package co.elasticsearch.enterprisesearch.client.model;

import co.elasticsearch.enterprisesearch.client.model.request.filter.DateValueFilter;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.experimental.UtilityClass;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class DeserializationUtil {
    public JsonNode getFirstValue(TreeNode filterNode){
        if(filterNode instanceof ArrayNode){
            return ((ArrayNode) filterNode).elements().next();
        }else{
            return (JsonNode) filterNode;
        }
    }

    public Stream<JsonNode> getNodeStream(TreeNode filterNode){
        if (filterNode instanceof ArrayNode) {
            ArrayNode filterValues = (ArrayNode) filterNode;
            return StreamSupport.stream(filterValues.spliterator(), false);
        } else {
            return Stream.of( (JsonNode) filterNode);
        }
    }

    public boolean isDate(TextNode text) {
        try {
            OffsetDateTime.parse(text.asText(), DateValueFilter.RFC_3339);
            return true;
        } catch (DateTimeParseException e) {
            return false;
            //Intentionally Blank
        }
    }
}
