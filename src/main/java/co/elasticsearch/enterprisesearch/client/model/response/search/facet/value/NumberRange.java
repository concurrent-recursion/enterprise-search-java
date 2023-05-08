package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Represents a numeric range facet value
 */
@Getter
@Setter
@Accessors(chain = true)
public class NumberRange implements FacetRangeValue<BigDecimal> {
    /**
     * The start of the range, optional
     * @param from the start of the range
     * @return the start of the range, or null if no start is defined
     */
    private BigDecimal from;
    /**
     * The end of the range, optional
     * @param to the end of the range
     * @return the end of the range, or null if no end is defined
     */
    private BigDecimal to;
    /**
     * The count of documents matching this number range
     * @param count the count of documents
     * @return the count of documents
     */
    private Long count;
    /**
     * The range name
     * @param name the name of the range
     * @return the name of the range
     */
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
