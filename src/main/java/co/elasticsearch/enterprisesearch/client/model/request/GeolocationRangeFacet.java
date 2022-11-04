package co.elasticsearch.enterprisesearch.client.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)

public class GeolocationRangeFacet extends RangeFacet {
    /**
     * The mode of the distribution, specified as a latitude-longitude pair
     */
    private BigDecimal[] center;
    /**
     * The base unit of measurement: mm, cm, m (meters), km, in, ft, yd, or mi (mile)
     */
    @Pattern(regexp = "mm|cm|m|km|in|ft|yd|mi")
    private String unit;
}
