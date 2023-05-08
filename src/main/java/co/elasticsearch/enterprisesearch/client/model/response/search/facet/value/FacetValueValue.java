package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

/**
 * Represents a facet value for a single value
 * @param <T> The type of value for this facet value
 */
public interface FacetValueValue<T> extends FacetValue {
    /**
     * Get the value for this facet value
     * @return The value for this facet value
     */
    T getValue();

}
