package co.elasticsearch.enterprisesearch.client;

public class ElasticClientException extends RuntimeException{
    public ElasticClientException() {
        super();
    }

    public ElasticClientException(String message) {
        super(message);
    }

    public ElasticClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElasticClientException(Throwable cause) {
        super(cause);
    }
}
