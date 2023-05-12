package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.AppSearchErrorResponseException;
import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import dev.failsafe.RetryPolicy;
import dev.failsafe.Timeout;
import dev.failsafe.okhttp.FailsafeCall;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.net.ProtocolException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static co.elasticsearch.enterprisesearch.client.AppSearchClient.APP_JSON;

@Slf4j
@UtilityClass
class ClientUtils {
    public <T> T marshalResponse(final OkHttpClient client, final Request request, final ObjectMapper mapper, final Class<T> responseType){
        log.debug("Making HttpRequest {} {}",request.method(),request.url());
        FailsafeCall failsafeCall = FailsafeCall
                .with(RetryPolicy.ofDefaults())
                .compose(client.newCall(request));

        try(Response response = failsafeCall.execute()){
            if(response.body() == null){
                if(responseType == null){
                    return null;
                }else {
                    throw new ElasticServerException("Server Response is empty, Expecting " + responseType);
                }
            }
            return marshal(mapper,response,responseType);
        }catch(IOException e){
            log.error("Exception during request",e);
            throw new ElasticServerException(e);
        }
    }
    public <T> T marshalResponse(final OkHttpClient client, final Request request, final ObjectMapper mapper, final JavaType responseType){
        log.debug("Making HttpRequest {} {}",request.method(),request.url());
        FailsafeCall failsafeCall = FailsafeCall
                .with(RetryPolicy.ofDefaults())
                .compose(client.newCall(request));
        try(Response response = failsafeCall.execute()){

            if(response.body() == null){
                if(responseType == null){
                    return null;
                }else {
                    throw new ElasticServerException("Server Response is empty, Expecting " + responseType);
                }
            }
            if(response.code() == 401){
                ErrorResponse errorResponse = marshal(mapper,response, ErrorResponse.class);
                throw new AppSearchErrorResponseException(errorResponse,response.code());
            }
            return marshal(mapper,response,responseType);
        }catch(IOException e){
            log.error("Exception during request",e);
            throw new ElasticServerException(e);
        }
    }


    private <T> T marshal(final ObjectMapper mapper, Response response,JavaType responseType) throws IOException{
        String responseBody = response.body().string();
        try {
            return mapper.readValue(responseBody, responseType);
        }catch(MismatchedInputException oops){
            ErrorResponse err = attemptErrorResponseMarshal(mapper,responseBody,oops);
            throw new AppSearchErrorResponseException(err,response.code());
        }catch(IOException e){
            log.error("Exception parsing response: {}",responseBody);
            throw new ElasticServerException(e);
        }
    }

    private ErrorResponse attemptErrorResponseMarshal(final ObjectMapper mapper, String responseBody, final MismatchedInputException cause) throws ElasticClientException{
        try{
            return mapper.readValue(responseBody, ErrorResponse.class);
        }catch (Exception e){
            log.trace("Exception trying to marshal an ErrorResponse, probably caused by the original marshalling being incorrect",e);
            throw new ElasticClientException("Unable to parse response",cause);
        }
    }

    private <T> T marshal(final ObjectMapper mapper, Response response,Class<T> responseType) throws IOException{
        String responseBody = response.body().string();
        try {
            return mapper.readValue(responseBody, responseType);
        }catch(MismatchedInputException oops){
            ErrorResponse err = attemptErrorResponseMarshal(mapper,responseBody,oops);
            throw new AppSearchErrorResponseException(err,response.code());
        }catch(IOException e){
            log.error("Exception parsing response: {}",responseBody);
            throw new ElasticServerException(e);
        }
    }

    public void addPagination(final HttpUrl.Builder urlBuilder,final Page page){
        if(page != null) {
            if (page.getCurrent() != null) {
                urlBuilder.addQueryParameter("page[current]", String.valueOf(page.getCurrent()));
            }
            if (page.getSize() != null) {
                urlBuilder.addQueryParameter("page[size]", String.valueOf(page.getSize()));
            }
        }
    }

    public RequestBody marshalPayload(final ObjectMapper mapper, final Object payload){
        try{
            return RequestBody.create(mapper.writeValueAsBytes(payload),APP_JSON);
        }catch (IOException e){
            throw new ElasticClientException(e);
        }
    }




}
