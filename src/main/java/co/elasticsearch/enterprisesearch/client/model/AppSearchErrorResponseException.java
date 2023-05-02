package co.elasticsearch.enterprisesearch.client.model;

import co.elasticsearch.enterprisesearch.client.model.response.ErrorResponse;
import lombok.Getter;

/**
 * Represents an error extracting the response document from the AppSearch Server
 */
@Getter
public class AppSearchErrorResponseException extends RuntimeException{
    /**
     * The ErrorResponse to return
     * @return The error response
     */
    private final ErrorResponse errorResponse;
    /**
     * The HTTP Status code to return
     * @return the status code
     */
    private final Integer responseStatus;

    /**
     * Create an exception with the given error response
     * @param response The response to return to the caller
     */
    public AppSearchErrorResponseException(ErrorResponse response){
        super();
        this.errorResponse = response;
        this.responseStatus = null;
    }

    /**
     * Create an exception with the given error response and status code
     * @param response The response to return to the caller
     * @param responseStatus The HTTP response status to return
     */
    public AppSearchErrorResponseException(ErrorResponse response, Integer responseStatus){
        super();
        this.errorResponse = response;
        this.responseStatus = responseStatus;
    }

}
