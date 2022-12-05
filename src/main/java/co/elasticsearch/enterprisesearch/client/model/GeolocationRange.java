package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GeolocationRange {

    private GeoLocation center;
    private BigDecimal distance;
    /**
     * The base unit of measurement: mm, cm, m (meters), km, in, ft, yd, or mi (mile)
     */
    private GeoLocation.Unit unit;


    private BigDecimal from;
    private BigDecimal to;
}
