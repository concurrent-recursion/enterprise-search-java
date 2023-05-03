package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Indicates the type of Facet
 */
@Getter
@RequiredArgsConstructor
public enum FacetType {
    VALUE("value"),RANGE("range");

    /**
     * The text representation of the FacetType
     * @return The facet type value
     */
    @JsonValue
    private final String value;

    /**
     * Gets the facet type by value
     * @param value the value of the facet type
     * @return the matching FacetType, otherwise throws an exception
     */
    @JsonCreator
    public static FacetType fromValue(String value){
        return Arrays.stream(values()).filter(m -> m.value.equals(value)).findFirst().orElseThrow();
    }

}
