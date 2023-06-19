package co.elasticsearch.enterprisesearch.client.model.request.search.filter;

import co.elasticsearch.enterprisesearch.client.model.request.search.range.DateRange;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.Objects;

import static co.elasticsearch.enterprisesearch.client.model.request.search.filter.DateValueFilter.DATE_PATTERN;

/**
 * Represents a Search Date Range filter
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@ToString
public class DateRangeFilter implements Filter, FieldFilter {
    /**
     * The field from your schema upon which to apply your filter
     * @return The field name
     */
    private final String name;
    /**
     * Inclusive lower bound of the range. Is required if to is not given.
     * @param from the lower bound
     * @return the lower bound
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private OffsetDateTime from;
    /**
     * Exclusive upper bound of the range. Is required if from is not given.
     * @param to the upper bound
     * @return the upper bound
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private OffsetDateTime to;

    /**
     * Set the range, upper bound exclusive, lower bound inclusive
     * @param range The range
     * @return This filter
     */
    public DateRangeFilter setRange(DateRange range) {
        this.from = range == null ? null : range.getFrom();
        this.to = range == null ? null : range.getTo();
        return this;
    }

    /**
     * Get the filter range
     * @return The filter range
     */
    public DateRange getRange() {
        return from == null && to == null ? null : new DateRange(from, to);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateRangeFilter)) return false;
        DateRangeFilter that = (DateRangeFilter) o;
        return name.equals(that.name) && Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, from, to);
    }
}
