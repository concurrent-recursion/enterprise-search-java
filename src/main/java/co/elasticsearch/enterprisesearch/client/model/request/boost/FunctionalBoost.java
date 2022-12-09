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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"type", "function", "operation", "factor"})
public class FunctionalBoost implements Boost {

    public BoostType getType() {
        return BoostType.FUNCTIONAL;
    }

    /**
     * Type of function to calculate the boost value. Can be linear, exponential, or gaussian. All functions decay rapidly towards 0 after about 2 days because the decay scale is 1 day.
     * @param function The function to calculate the boost value
     * @return The function
     */
    private Function function;
    /**
     * The arithmetic operation used to combine the original document score with your boost value. Can be add or multiply. Defaults to add.
     * @param operation The operation used to combine the original document with your boost
     * @return The operation
     */
    private Operation operation;
    private BigDecimal factor;
    private String name;


}
