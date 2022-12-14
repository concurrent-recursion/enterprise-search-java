package co.elasticsearch.enterprisesearch.client;

public class ElasticServerException extends RuntimeException{
    public ElasticServerException() {
        super();
    }

    public ElasticServerException(String message) {
        super(message);
    }

    public ElasticServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElasticServerException(Throwable cause) {
        super(cause);
    }
}
