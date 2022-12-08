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

    private Function function;
    private Operation operation;
    private BigDecimal factor;
    private String name;


}
