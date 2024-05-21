package co.elasticsearch.enterprisesearch.client.model.request.search.filter;


/**
 * Interface for identifying filters on a field
 */
public interface FieldFilter {
    /**
     * The name of the filter
     * @return The name of the filter
     */
    String getName();
}
