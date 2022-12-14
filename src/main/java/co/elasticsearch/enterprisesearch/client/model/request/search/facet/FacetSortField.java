package co.elasticsearch.enterprisesearch.client.model.request.search.facet;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum FacetSortField {
    COUNT("count"),VALUE("value");
    @JsonValue
    private final String name;

    public static FacetSortField fromValue(String value){
        return Arrays.stream(values()).filter(v -> v.name.equals(value)).findFirst().orElseThrow();
    }
}
