package co.elasticsearch.enterprisesearch.client.model.request.filter;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class GeolocationFilter implements Filter{
    /**
     * The mode of the distribution, specified as a latitude-longitude pair
     */
    private BigDecimal[] center;
    /**
     * The base unit of measurement: mm, cm, m (meters), km, in, ft, yd, or mi (mile)
     */
    @Pattern(regexp = "mm|cm|m|km|in|ft|yd|mi")
    private String unit;

    private BigDecimal distance;
    private BigDecimal from;
    private BigDecimal to;
}
