package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Represents Geolocation Range
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GeolocationRange {


    /**
     * The mode of the distribution, specified as a latitude-longitude pair
     * @param center The center of the distribution
     * @return the center
     */
    private GeoLocation center;
    /**
     * A number representing the distance unit. Is required if from or to is not given.
     * @param distance the distance
     * @return the distance
     */
    private BigDecimal distance;
    /**
     * The base unit of measurement
     * @param unit the unit
     * @return the unit
     */
    private GeoLocation.Unit unit;


    /**
     * Inclusive lower bound of the range. Is required if to is not given.
     * @param from the lower bound
     * @return the lower bound
     */
    private BigDecimal from;
    /**
     * Exclusive upper bound of the range. Is required if from is not given.
     * @param to the upper bound
     * @return teh upper bound
     */
    private BigDecimal to;
}
