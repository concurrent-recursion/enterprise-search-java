package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

/**
 * Represents a facet value for a Date range
 */
@Getter
@Setter
@Accessors(chain = true)
public class DateRange implements FacetRangeValue<OffsetDateTime> {
    /**
     * The start of the range, optional
     * @param from The start of the date range
     * @return the start of the date range, or null if no start range is defined
     */
    private OffsetDateTime from;
    /**
     * The end of the range, optional
     * @param to The end of the date range
     * @return the end of the date range, or null if no start range is defined
     */
    private OffsetDateTime to;
    /**
     * The number of documents matching this range
     * @param count the count of documents
     * @return the count of documents matching this range
     */
    private Long count;
    /**
     * The name of this date range, optional
     * @param name the name for this range
     * @return the name for this range, or null if no value is defined
     */
    private String name;
}
