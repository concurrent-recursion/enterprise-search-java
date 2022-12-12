package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.response.documents.DeleteResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.ListResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static co.elasticsearch.enterprisesearch.client.AppSearchClient.APP_JSON;

@Slf4j
@RequiredArgsConstructor
public class DocumentsApiImpl<T> implements DocumentsApi<T>{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;
    private final Class<T> resultType;
    @Override
    public IndexResponse index(String engineName, List<T> documents) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + "/api/as/v1/engines/{engineName}/documents")).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Indexing {} documents:{}",documents.size(), url);
        Request okRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create(objectMapper.writeValueAsBytes(documents), APP_JSON))
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, IndexResponse.IndexResult.class);
            List<IndexResponse.IndexResult> results = objectMapper.readValue(response.body().byteStream(),listType);
            return new IndexResponse().setDocuments(results);
        }
    }

    @Override
    public DeleteResponse delete(String engineName, List<String> ids) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + "/api/as/v1/engines/{engineName}/documents")).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Indexing documents:{}", url);
        Request okRequest = new Request.Builder()
                .url(url)
                .delete(RequestBody.create(objectMapper.writeValueAsBytes(ids), APP_JSON))
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(), DeleteResponse.class);
        }
    }

    @Override
    public ListResponse<T> listDocuments(String engineName, Page page) {
        return null;
    }
}
