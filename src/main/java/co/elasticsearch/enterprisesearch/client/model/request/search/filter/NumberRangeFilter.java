package co.elasticsearch.enterprisesearch.client.model.request.search.filter;

import co.elasticsearch.enterprisesearch.client.model.request.search.range.NumberRange;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a Number Range Filter for a search request
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@ToString
public class NumberRangeFilter implements Filter, FieldFilter {
    /**
     * The field from your schema upon which to apply your filter
     * @param name the field name
     * @return the field name
     */
    private final String name;
    /**
     * Inclusive lower bound of the range. Is required if to is not given.
     * @param from the lower bound
     * @return the lower bound
     */
    private BigDecimal from;
    /**
     * Exclusive upper bound of the range. Is required if from is not given.
     * @param to the upper bound
     * @return the upper bound
     */
    private BigDecimal to;

    /**
     * Create a NumberRangeFilter with the given name, start, and end
     * @param name the filter name
     * @param from the filter start
     * @param to the filter end
     */
    public NumberRangeFilter(String name, BigDecimal from, BigDecimal to) {
        this(name);
        this.from = from;
        this.to = to;
    }

    /**
     * Create a NumberRangeFilter with the given name, start, and end
     * @param name the filter name
     * @param from the filter start
     * @param to the filter end
     */
    public NumberRangeFilter(String name, Integer from, Integer to) {
        this(name);
        if (from != null) {
            this.from = new BigDecimal(from);
        }
        if (to != null) {
            this.to = new BigDecimal(to);
        }
    }

    /**
     * Create a NumberRangeFilter with the given name, start, and end
     * @param name the filter name
     * @param from the filter start
     * @param to the filter end
     */
    public NumberRangeFilter(String name, Long from, Long to) {
        this(name);
        if (from != null) {
            this.from = new BigDecimal(from);
        }
        if (to != null) {
            this.to = new BigDecimal(to);
        }
    }

    /**
     * Create a NumberRangeFilter with the given name, start, and end
     * @param name the filter name
     * @param from the filter start
     * @param to the filter end
     */
    public NumberRangeFilter(String name, Float from, Float to) {
        this(name);
        if (from != null) {
            this.from = BigDecimal.valueOf(from);
        }
        if (to != null) {
            this.to = BigDecimal.valueOf(to);
        }
    }

    /**
     * Create a NumberRangeFilter with the given name, start, and end
     * @param name the filter name
     * @param from the filter start
     * @param to the filter end
     */
    public NumberRangeFilter(String name, Double from, Double to) {
        this(name);
        if (from != null) {
            this.from = BigDecimal.valueOf(from);
        }
        if (to != null) {
            this.to = BigDecimal.valueOf(to);
        }
    }

    /**
     * Set the range
     * @param range The range for this filter
     * @return This filter
     */
    public NumberRangeFilter setRange(NumberRange range) {
        this.from = range == null ? null : range.getFrom();
        this.to = range == null ? null : range.getTo();
        return this;
    }
    /**
     * Get the range for this filter
     * @return The range
     */
    public NumberRange getRange() {
        return from == null && to == null ? null : new NumberRange(from, to);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberRangeFilter)) return false;
        NumberRangeFilter that = (NumberRangeFilter) o;
        return name.equals(that.name) && Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, from, to);
    }
}
