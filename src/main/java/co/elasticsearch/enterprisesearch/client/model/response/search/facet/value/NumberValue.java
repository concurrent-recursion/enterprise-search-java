package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Represents a numeric facet value
 */
@Getter
@Setter
@Accessors(chain = true)
public class NumberValue implements FacetValueValue<BigDecimal> {
    /**
     * The facet value
     * @param value the value of the facet
     * @return the value of the facet
     */
    private BigDecimal value;
    /**
     * The count of the facet value
     * @param count the count of documents matching the value
     * @return the count of documents matching the value
     */
    private Long count;

    /**
     * The raw Long representation of the facet value
     * @return The Long value
     * @throws ArithmeticException if this has a nonzero fractional part, or will not fit in a long
     */
    @JsonIgnore
    public Long getValueLong() {
        return value == null ? null : value.longValueExact();
    }

    /**
     * The raw Integer representation of the facet value
     * @return The Integer value
     * @throws ArithmeticException if this has a nonzero fractional part, or will not fit in an int
     */
    @JsonIgnore
    public Integer getValueInteger() {
        return value == null ? null : value.intValueExact();
    }
}
