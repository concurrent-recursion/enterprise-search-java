package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

/**
 * Represents a facet value
 */
public interface FacetValue {
    /**
     * Gets the count of documents matching this facet value
     * @return The count of documents matching this facet value
     */
    Long getCount();
}
