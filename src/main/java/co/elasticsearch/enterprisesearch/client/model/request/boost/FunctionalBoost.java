package co.elasticsearch.enterprisesearch.client.model.request.boost;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class FunctionalBoost implements Boost{
    private final String type = "functional";
    private Function function;
    private Operation operation;
    private BigDecimal factor;
}
