package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.response.documents.DeleteResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResult;
import co.elasticsearch.enterprisesearch.client.model.response.documents.ListResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class DocumentsApiImpl<T> implements DocumentsApi<T>{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;
    private final Class<T> resultType;

    private static final String DOCUMENTS_PATH = "/api/as/v1/engines/{engineName}/documents";
    @Override
    public IndexResponse index(String engineName, List<T> documents) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + DOCUMENTS_PATH)).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Indexing {} documents:{}",documents.size(), url);
        Request okRequest = new Request.Builder().url(url).post(ClientUtils.marshalPayload(objectMapper,documents)).build();
        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, IndexResult.class);
        List<IndexResult> results = ClientUtils.marshalResponse(client,okRequest,objectMapper,listType);
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
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,listType);
    }

    @Override
    public DeleteResponse delete(String engineName, List<String> ids) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + DOCUMENTS_PATH)).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Indexing documents:{}", url);
        Request okRequest = new Request.Builder().url(url).delete(ClientUtils.marshalPayload(objectMapper,ids)).build();
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,DeleteResponse.class);
    }

    @Override
    public ListResponse<T> listDocuments(String engineName, Page page){
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(baseUrl + DOCUMENTS_PATH)).newBuilder();
        urlBuilder.setPathSegment(4, engineName);
        ClientUtils.addPagination(urlBuilder,page);
        HttpUrl url = urlBuilder.build();
        Request okRequest = new Request.Builder().url(url).get().build();
        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, resultType);
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,listType);
    }
}
