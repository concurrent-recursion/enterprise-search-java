package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.client.model.GeoLocation;
import co.elasticsearch.enterprisesearch.client.model.Sort;
import co.elasticsearch.enterprisesearch.client.model.SortOrder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;

//TODO: See https://www.elastic.co/guide/en/app-search/current/api-reference.html#overview-api-references-geolocation
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@Accessors(chain = true)
public class GeoLocationSort implements SortOrder {

    private final GeoLocation center;
    private Mode mode;
    private Sort.Direction order;


    public GeoLocationSort(GeoLocation geoLocation) {
        center = geoLocation;
    }

    @Override
    public Sort.Direction getOrder() {
        return order;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Mode {
        MIN("min"),MAX("max"),MEDIAN("median"),AVERAGE("avg");
        @JsonValue
        private final String value;
        @JsonCreator
        public static Mode fromValue(String value){
            return Arrays.stream(values()).filter(m -> m.value.equals(value)).findFirst().orElseThrow();
        }
    }

}
