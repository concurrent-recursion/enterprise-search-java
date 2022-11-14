package co.elasticsearch.enterprisesearch.client.model.request.filter;

import co.elasticsearch.enterprisesearch.client.model.GeoLocation;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Accessors(chain = true)
public class GeolocationFilter implements Filter{
    /**
     * The mode of the distribution, specified as a latitude-longitude pair
     */
    private GeoLocation center;
    private BigDecimal distance;
    /**
     * The base unit of measurement: mm, cm, m (meters), km, in, ft, yd, or mi (mile)
     */
    private GeoLocation.Unit unit;


    private BigDecimal from;
    private BigDecimal to;
}
