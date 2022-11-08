package co.elasticsearch.enterprisesearch.client.model.request.boost;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class NumberProximityBoost implements Boost{
    private final String type = "proximity";
    /**
     * Factor to alter the impact of a boost on the score of a document. Must be between 0 and 10. Defaults to 1.0. A negative factor or fractional factor will not deboost a result.
     * @param factor A factor between 0.0 and 10.0
     */
    private BigDecimal factor;
}
