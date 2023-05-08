package co.elasticsearch.enterprisesearch.client.model.request.search.facet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Represents a facet sort type
 */
@RequiredArgsConstructor
@Getter
public enum FacetSortField {
    /**
     * Sort the facet values by count
     */
    COUNT("count"),
    /**
     * Sort the facet value by the values
     */
    VALUE("value");

    /**
     * The name of the facet
     * @param name the name of the facet
     * @return the name of the facet
     */
    @JsonValue
    private final String name;

    /**
     * Get a Facet Sort by value
     * @param value The value to lookup
     * @return The FacetSortField for the value, otherwise an exception will be thrown
     */
    @JsonCreator
    public static FacetSortField fromValue(String value){
        return Arrays.stream(values()).filter(v -> v.name.equals(value)).findFirst().orElseThrow();
    }
}
