package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.Page;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

import static co.elasticsearch.enterprisesearch.client.AppSearchClient.APP_JSON;

@Slf4j
@UtilityClass
class ClientUtils {
    public <T> T marshalResponse(final OkHttpClient client, final Request request, final ObjectMapper mapper, final Class<T> responseType){
        log.debug("Making HttpRequest {} {}",request.method(),request.url());
        try(Response response = client.newCall(request).execute()){
            log.trace("Response Code:{}",response.code());
            if(response.body() == null){
                if(responseType == null){
                    return null;
                }else {
                    throw new ElasticServerException("Server Response is empty");
                }
            }
            return mapper.readValue(response.body().byteStream(),responseType);
        }catch (IOException e){
            throw new ElasticServerException(e);
        }
    }
    public <T> T marshalResponse(final OkHttpClient client, final Request request, final ObjectMapper mapper, final JavaType responseType){
        log.debug("Making HttpRequest {} {}",request.method(),request.url());
        try(Response response = client.newCall(request).execute()){
            log.trace("Response Code:{}",response.code());
            if(response.body() == null){
                if(responseType == null){
                    return null;
                }else {
                    throw new ElasticServerException("Server Response is empty");
                }
            }
            return mapper.readValue(response.body().byteStream(),responseType);
        }catch (IOException e){
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
