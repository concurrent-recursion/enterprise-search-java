package co.elasticsearch.enterprisesearch.client.model.request.search;

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

/**
 * Represents a Geolocation Sort in a search request
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@Accessors(chain = true)
public class GeoLocationSort implements SortOrder {

    /**
     * Center point will be used to calculate the distance from that point to the field in each document, and return the
     * results ordered by distance. Center point can be specified using any of the geolocation field formats, see
     * geolocation fields.
     * @param center the center
     * @return the center
     */
    private final GeoLocation center;
    /**
     * How to calculate distance in case a field has several geo points. By default, the shortest distance is taken into account when sorting in ascending order and the longest distance when sorting in descending order.
     * @param mode the sort mode
     * @return the sort mode
     */
    private Mode mode;
    /**
     * The sort order
     * @param order the sort order
     * @return the sort order
     */
    private Sort.Order order;


    /**
     * Build a GeoLocationSort from a central point
     * @param geoLocation the centeral geolocation point
     */
    public GeoLocationSort(GeoLocation geoLocation) {
        center = geoLocation;
    }

    @Override
    public Sort.Order getOrder() {
        return order;
    }

    /**
     * How to calculate distance in case a field has several geo points. By default, the shortest distance is taken into account when sorting in ascending order and the longest distance when sorting in descending order.
     */
    @Getter
    @RequiredArgsConstructor
    public enum Mode {
        /**
         * Minimum
         */
        MIN("min"),
        /**
         * Maximum
         */
        MAX("max"),
        /**
         * Median
         */
        MEDIAN("median"),
        /**
         * Average
         */
        AVERAGE("avg");

        /**
         * The string value of the Mode
         * @return the string value
         */
        @JsonValue
        private final String value;

        /**
         * Get a Mode from the string value
         * @param value The value to match
         * @return The Mode with the matching value, otherwise an exception is thrown
         */
        @JsonCreator
        public static Mode fromValue(String value) {
            return Arrays.stream(values()).filter(m -> m.value.equals(value)).findFirst().orElseThrow();
        }
    }

}
