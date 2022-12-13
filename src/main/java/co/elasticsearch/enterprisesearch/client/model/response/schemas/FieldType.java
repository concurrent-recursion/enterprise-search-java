package co.elasticsearch.enterprisesearch.client.model.response.schemas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum FieldType {
    TEXT("text"),NUMBER("number"),DATE("date"),GEOLOCATION("geolocation"),ERROR("string");
    @JsonValue
    private final String value;

    @JsonCreator
    public static FieldType fromValue(String value){
        return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElseThrow();
    }
}
