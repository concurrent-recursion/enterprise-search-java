package co.elasticsearch.enterprisesearch.client.model.request.search.boost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a boost based on the value of a text field
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonPropertyOrder({"type", "value", "operation", "factor"})
public class TextValueBoost implements Boost {

    private String name;


    public BoostType getType() {
        return BoostType.VALUE;
    }

    /**
     * The value to exact match on.
     *
     * @param value One or more exact values to match on
     * @return the values
     */
    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    @JsonProperty("value")
    private List<String> value = new ArrayList<>();

    /**
     * The arithmetic operation used to combine the original document score with your boost value. Can be add or multiply. Defaults to add.
     *
     * @param operation The operation to use to boost the score
     * @return The operation
     */
    private Operation operation;

    private BigDecimal factor;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextValueBoost)) return false;
        TextValueBoost that = (TextValueBoost) o;
        return name.equals(that.name) && Objects.equals(value, that.value) && operation == that.operation && Objects.equals(factor, that.factor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, operation, factor);
    }
}
