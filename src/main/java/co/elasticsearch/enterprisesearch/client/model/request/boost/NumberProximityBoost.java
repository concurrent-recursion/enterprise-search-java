package co.elasticsearch.enterprisesearch.client.model.request.boost;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"type", "proximity", "function", "center", "factor"})
public class NumberProximityBoost implements Boost {
    private String name;

    public BoostType getType() {
        return BoostType.PROXIMITY;
    }

    /**
     * The mode of the distribution, scores based on proximity to this value
     *
     * @param center The mode of the distribution, scores based on proximity to this value
     */
    private BigDecimal center;
    /**
     * Type of function to calculate the boost value. Can be linear, exponential, or gaussian.
     *
     * @param function The function to calculate the boost
     */
    private Function function;

    /**
     * Factor to alter the impact of a boost on the score of a document. Must be between 0 and 10. Defaults to 1.0. A negative factor or fractional factor will not deboost a result.
     *
     * @param factor A factor between 0.0 and 10.0
     */
    private BigDecimal factor;
}
