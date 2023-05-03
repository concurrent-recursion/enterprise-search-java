package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class NumberRange implements FacetRangeValue<BigDecimal> {
    private BigDecimal from;
    private BigDecimal to;
    private Long count;
    private String name;

    /**
     * The raw Long representation of the facet value
     * @return The Long value
     * @throws ArithmeticException if this has a nonzero fractional part, or will not fit in a long
     */
    @JsonIgnore
    public Long getToLong() {
        return to == null ? null : to.longValueExact();
    }

    /**
     * The raw Integer representation of the facet value
     * @return The Integer value
     * @throws ArithmeticException if this has a nonzero fractional part, or will not fit in an int
     */
    @JsonIgnore
    public Integer getToInteger() {
        return to == null ? null : to.intValueExact();
    }

    /**
     * The raw Long representation of the facet value
     * @return The Long value
     * @throws ArithmeticException if this has a nonzero fractional part, or will not fit in a long
     */
    @JsonIgnore
    public Long getFromLong() {
        return from == null ? null : from.longValueExact();
    }

    /**
     * The raw Integer representation of the facet value
     * @return The Integer value
     * @throws ArithmeticException if this has a nonzero fractional part, or will not fit in an int
     */
    @JsonIgnore
    public Integer getFromInteger() {
        return from == null ? null : from.intValueExact();
    }

}
