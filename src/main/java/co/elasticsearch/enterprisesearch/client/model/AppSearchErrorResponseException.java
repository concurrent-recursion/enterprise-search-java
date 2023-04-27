package co.elasticsearch.enterprisesearch.client.model;

import co.elasticsearch.enterprisesearch.client.model.response.ErrorResponse;
import lombok.Getter;

@Getter
public class AppSearchErrorResponseException extends RuntimeException{
    private final ErrorResponse errorResponse;
    private final Integer responseStatus;
    public AppSearchErrorResponseException(ErrorResponse response){
        super();
        this.errorResponse = response;
        this.responseStatus = null;
    }

    public AppSearchErrorResponseException(ErrorResponse response, Integer responseStatus){
        super();
        this.errorResponse = response;
        this.responseStatus = responseStatus;
    }

}
