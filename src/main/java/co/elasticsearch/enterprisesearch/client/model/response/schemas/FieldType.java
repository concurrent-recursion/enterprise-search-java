package co.elasticsearch.enterprisesearch.client.model.response.schemas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Represents the field type
 */
@RequiredArgsConstructor
@Getter
public enum FieldType {
    /**
     * Text type field
     */
    TEXT("text"),
    /**
     * Number type field
     */
    NUMBER("number"),
    /**
     * Date type field
     */
    DATE("date"),
    /**
     * Geolocation type field
     */
    GEOLOCATION("geolocation");

    /**
     * The string value of this enum
     * @return the string representation
     */
    @JsonValue
    private final String value;

    /**
     * Get a field type based on value
     * @param value The value to lookup
     * @return The FieldType matching the value, otherwise throw an exception
     */
    @JsonCreator
    public static FieldType fromValue(String value){
        return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElseThrow();
    }
}
