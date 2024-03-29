package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.AppSearchErrorResponseException;
import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.response.documents.DeleteResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResult;
import co.elasticsearch.enterprisesearch.client.model.response.documents.ListResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
class DocumentsApiImpl<T> implements DocumentsApi<T>{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;
    private final Class<T> resultType;

    private static final String DOCUMENTS_PATH = "/api/as/v1/engines/{engineName}/documents";

    private RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
            .handle(AppSearchErrorResponseException.class)
            .handle(IOException.class)
            .withBackoff(Duration.ofSeconds(2),Duration.ofSeconds(128))
            .build();

    /**
     * Set the Failsafe Retry policy for all the Documents API calls
     * @param delay retry delay
     * @param maxDelay maximum delay
     * @param delayFactor the retry delay factor
     * @return The DocumentsApi
     */
    public DocumentsApi<T> setBackoff(@NotNull Duration delay, @NotNull Duration maxDelay, double delayFactor ){
        this.retryPolicy =  RetryPolicy.builder()
                .handle(AppSearchErrorResponseException.class)
                .handle(IOException.class)
                .withBackoff(delay,maxDelay,delayFactor)
                .build();
        return this;
    }



    public DocumentsApi<T> disableRetries(){
        this.retryPolicy = RetryPolicy.builder().withMaxRetries(0).build();
        return this;
    }


    @Override
    public IndexResponse index(String engineName, List<T> documents) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + DOCUMENTS_PATH)).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Indexing {} documents:{}",documents.size(), url);
        Request okRequest = new Request.Builder().url(url).post(ClientUtils.marshalPayload(objectMapper,documents)).build();
        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, IndexResult.class);

        List<IndexResult> results = Failsafe.with(retryPolicy).get(() -> ClientUtils.marshalResponse(client,okRequest,objectMapper,listType));
        return new IndexResponse().setDocuments(results);
    }

    @Override
    public List<T> getDocuments(String engineName, List<String> ids) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(baseUrl + DOCUMENTS_PATH)).newBuilder();
        urlBuilder.setPathSegment(4, engineName);
        if(ids != null && !ids.isEmpty()){
            try {
                urlBuilder.addQueryParameter(engineName, objectMapper.writeValueAsString(ids));
            }catch(IOException e){
                throw new ElasticClientException(e);
            }
        }
        HttpUrl url = urlBuilder.build();
        log.debug("Getting documents:{}", url);
        Request okRequest = new Request.Builder().url(url).get().build();
        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, resultType);

        return Failsafe.with(retryPolicy).get(() -> ClientUtils.marshalResponse(client,okRequest,objectMapper,listType));
    }

    @Override
    public DeleteResponse delete(String engineName, List<String> ids) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + DOCUMENTS_PATH)).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Indexing documents:{}", url);
        Request okRequest = new Request.Builder().url(url).delete(ClientUtils.marshalPayload(objectMapper,ids)).build();
        return Failsafe.with(retryPolicy).get(() -> ClientUtils.marshalResponse(client,okRequest,objectMapper,DeleteResponse.class));
    }

    @Override
    public ListResponse<T> listDocuments(String engineName, Page page){
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(baseUrl + DOCUMENTS_PATH + "/list")).newBuilder();
        urlBuilder.setPathSegment(4, engineName);
        ClientUtils.addPagination(urlBuilder,page);
        HttpUrl url = urlBuilder.build();
        Request okRequest = new Request.Builder().url(url).get().build();

        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, resultType);
        return Failsafe.with(retryPolicy).get(() -> ClientUtils.marshalResponse(client,okRequest,objectMapper,listType));
    }
}
