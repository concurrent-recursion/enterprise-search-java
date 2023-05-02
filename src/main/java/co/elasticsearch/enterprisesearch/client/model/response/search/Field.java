package co.elasticsearch.enterprisesearch.client.model.response.search;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Represents a field in the engine's schema
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface Field {
    /**
     * Get the raw value in this field
     * @return The raw value
     */
    Object getRaw();
}
