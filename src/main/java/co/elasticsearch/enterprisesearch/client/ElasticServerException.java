package co.elasticsearch.enterprisesearch.client;

/**
 * Represents an exception caused or returned by the elasticsearch server
 */
public class ElasticServerException extends RuntimeException{
    /**
     * Create a blank server exception
     */
    public ElasticServerException() {
        super();
    }

    /**
     * Create a server exception with an error message
     * @param message The exception error message
     */
    public ElasticServerException(String message) {
        super(message);
    }

    /**
     * Create a server exception with an error message and cause
     * @param message The exception error message
     * @param cause The cause of the exception
     */
    public ElasticServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a server exception with a cause
     * @param cause The cause of the exception
     */
    public ElasticServerException(Throwable cause) {
        super(cause);
    }
}
