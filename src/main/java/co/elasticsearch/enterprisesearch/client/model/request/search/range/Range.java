package co.elasticsearch.enterprisesearch.client.model.request.search.range;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents a generic Range
 * @param <T> The containing type of the range
 */
@JsonDeserialize(using = RangeDeserializer.class)
public interface Range<T> {
    /**
     * Get the start of the range
     * @return The start of the range
     */
    T getFrom();

    /**
     * Get the end of the range
     * @return The end of the range
     */
    T getTo();
}
