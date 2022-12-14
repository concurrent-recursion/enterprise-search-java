package co.elasticsearch.enterprisesearch.client.model.response;

/**
 * Interface to represent a response that can contain errors
 */
public interface ErrorableResponse {
    /**
     * Gets whether this response contains any errors from the requested operation
     * @return true if 1 or more errors occurred, otherwise false
     */
    boolean isError();
}
