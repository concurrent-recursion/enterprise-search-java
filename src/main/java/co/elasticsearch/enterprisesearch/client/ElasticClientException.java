package co.elasticsearch.enterprisesearch.client;

/**
 * Represents an exception on the client's side of the request
 */
public class ElasticClientException extends RuntimeException{
    /**
     * Create an empty exception
     */
    public ElasticClientException() {
        super();
    }
    /**
     * Create an empty exception with a message
     * @param message the exception message
     */
    public ElasticClientException(String message) {
        super(message);
    }
    /**
     * Create an empty exception with a message
     * @param message the exception message
     * @param cause The root cause of the exception
     */
    public ElasticClientException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Create an empty exception with a root exception
     * @param cause The root cause of the exception
     */
    public ElasticClientException(Throwable cause) {
        super(cause);
    }
}
