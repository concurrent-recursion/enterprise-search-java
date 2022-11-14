package co.elasticsearch.enterprisesearch.client.model.request.boost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"type","value","operation","factor"})
public class ValueBoost implements Boost{
    private final String type = "value";
    /**
     * The value to exact match on.
     * @param values One or more values to match on
     */
    @JsonProperty("value")
    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    private List<String> values = new ArrayList<>();

    /**
     * The arithmetic operation used to combine the original document score with your boost value. Can be add or multiply. Defaults to add.
     * @param operation The operation to use to boost the score
     */
    private Operation operation;
    /**
     * Factor to alter the impact of a boost on the score of a document.
     * Must be between 0 and 10. Defaults to 1.0. A negative factor or fractional factor will not deboost a result.
     */
    private BigDecimal factor;
}
