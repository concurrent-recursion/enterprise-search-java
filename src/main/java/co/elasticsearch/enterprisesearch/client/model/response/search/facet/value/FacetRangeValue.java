package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

/**
 * Represents a Facet range value.
 * @param <T> The type of values represented by the range
 */
public interface FacetRangeValue<T> extends FacetValue {
    /**
     * The start of the facet range, optional
     * @return The start of the facet range, or null if no start is defined
     */
    T getFrom();

    /**
     * The end of the facet range, optional
     * @return The end of the facet range, or null if no start is defined
     */
    T getTo();

}
