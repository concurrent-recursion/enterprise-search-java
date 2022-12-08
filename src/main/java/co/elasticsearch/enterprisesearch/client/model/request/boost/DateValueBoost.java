package co.elasticsearch.enterprisesearch.client.model.request.boost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DateValueBoost implements Boost {

    private String name;


    public BoostType getType() {
        return BoostType.VALUE;
    }

    /**
     * The value to exact match on.
     *
     * @param values One or more values to match on
     */
    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    @JsonProperty("value")
    private List<OffsetDateTime> value = new ArrayList<>();

    /**
     * The arithmetic operation used to combine the original document score with your boost value. Can be add or multiply. Defaults to add.
     *
     * @param operation The operation to use to boost the score
     */
    private Operation operation;
    /**
     * Factor to alter the impact of a boost on the score of a document.
     * Must be between 0 and 10. Defaults to 1.0. A negative factor or fractional factor will not deboost a result.
     */
    private BigDecimal factor;


}
