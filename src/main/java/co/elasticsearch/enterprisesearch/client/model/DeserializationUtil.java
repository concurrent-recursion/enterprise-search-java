package co.elasticsearch.enterprisesearch.client.model;

import co.elasticsearch.enterprisesearch.client.model.request.search.filter.DateValueFilter;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.experimental.UtilityClass;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Common utilities for working with JSON
 */
@UtilityClass
public class DeserializationUtil {
    /**
     * Get the first value in a node
     * @param filterNode If this node is an array, get the first value, otherwise return this node
     * @return The JsonNode or the first element in the array
     */
    public JsonNode getFirstValue(TreeNode filterNode) {
        if (filterNode instanceof ArrayNode && !((ArrayNode) filterNode).isEmpty()) {
            return ((ArrayNode) filterNode).elements().next();
        } else {
            return (JsonNode) filterNode;
        }
    }

    /**
     * Gets a stream of the child nodes if filterNode is array, otherwise returns a stream containing only the filterNode
     * @param filterNode The node to stream
     * @return The filterNode if its not an array, other the filter of the array
     */
    public Stream<JsonNode> getNodeStream(TreeNode filterNode) {
        if (filterNode instanceof ArrayNode) {
            ArrayNode filterValues = (ArrayNode) filterNode;
            return StreamSupport.stream(filterValues.spliterator(), false);
        } else {
            return Stream.of((JsonNode) filterNode);
        }
    }

    /**
     * Determines if the date field will successfully parse
     * @param text The TextNode that contains the date
     * @return true if the TextNode represents a date, otherwise falue
     */
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
