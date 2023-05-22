package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

/**
 * Represents a Date value for a facet
 */
@Getter
@Setter
@Accessors(chain = true)
public class DateValue implements FacetValueValue<OffsetDateTime> {
    /**
     * The DateTime of the facet value
     *
     * @param value the datetime for the facet value
     * @return The datetime for the facet value
     */
    private OffsetDateTime value;
    /**
     * The count of documents matching this facet value
     *
     * @param count the count of documents matching this value
     * @return the count of documents matching this value
     */
    private Long count;
}
