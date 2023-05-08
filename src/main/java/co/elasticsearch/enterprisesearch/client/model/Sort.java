package co.elasticsearch.enterprisesearch.client.model;

import co.elasticsearch.enterprisesearch.client.model.request.search.GeoLocationSort;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a sort order
 */
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonSerialize(using = SortSerializer.class)
public class Sort {
    /**
     * Default sorting for documents, score
     */
    public static final String SCORE = "_score";

    /**
     * The sort name
     * @param name the sort name
     * @return the sort name
     */
    @JsonKey
    private final String name;
    /**
     * The sort order
     * @param direction the sort order
     * @return the sort order
     */
    @JsonValue
    private final SortOrder direction;


    /**
     * Represents a sort order
     */
    @Getter
    @RequiredArgsConstructor
    public enum Order implements SortOrder {
        /**
         * Ascending order
         */
        ASCENDING("asc"),
        /**
         * Descending order
         */
        DESCENDING("desc");

        /**
         * The string representation of the order
         * @param value The sort direction value
         * @return the sort direction value
         */
        @JsonValue
        private final String value;

        /**
         * finds the order with the given value
         * @param value The value to find
         * @return The sort Order with the matching value, or an exception is thrown
         */
        @JsonCreator
        public static Order fromValue(String value) {
            return Arrays.stream(values()).filter(m -> m.value.equals(value)).findFirst().orElseThrow();
        }

        /**
         * The order of the sort
         * @return The order
         */
        @Override
        public Order getOrder() {
            return this;
        }
    }


    /**
     * Create a sort with the given name and order
     * @param field The field to sort on
     * @param order The order to sort results
     */
    public Sort(String field, @NonNull Sort.Order order) {
        this.name = field;
        this.direction = order;
    }

    /**
     * Create a sort with the given name and location
     * @param field The field name to sort on
     * @param location The location to sort on;
     */
    public Sort(String field, GeoLocationSort location) {
        this.name = field;
        this.direction = location;
    }

    /**
     * Creates a sort from a json node
     * @param node The node to marshall into an object
     * @return The marshalled Sort object
     */
    @JsonCreator
    static Sort createSort(ObjectNode node) {
        Map.Entry<String, JsonNode> field = node.fields().next();
        if (field.getValue().getNodeType().equals(JsonNodeType.STRING)) {
            return new Sort(field.getKey(), Order.fromValue(field.getValue().asText()));
        } else {
            JsonNode centerNode = field.getValue().get("center");
            JsonNode modeNode = field.getValue().get("mode");
            JsonNode orderNode = field.getValue().get("order");
            final GeoLocationSort geoLocationSort;
            if (centerNode.isArray()) {
                geoLocationSort = new GeoLocationSort(new GeoLocation(centerNode.get(0).asText(), centerNode.get(1).asText()));
            } else {
                geoLocationSort = new GeoLocationSort(new GeoLocation(centerNode.asText()));
            }
            geoLocationSort.setMode(GeoLocationSort.Mode.fromValue(modeNode.textValue()));
            geoLocationSort.setOrder(Order.fromValue(orderNode.textValue()));
            return new Sort(field.getKey(), geoLocationSort);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sort)) return false;
        Sort sort = (Sort) o;
        return name.equals(sort.name) && direction.equals(sort.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, direction);
    }
}
