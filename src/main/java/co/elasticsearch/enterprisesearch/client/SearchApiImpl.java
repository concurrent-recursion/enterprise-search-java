package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.search.MultiSearchRequest;
import co.elasticsearch.enterprisesearch.client.model.request.search.SearchRequest;
import co.elasticsearch.enterprisesearch.client.model.response.search.MultiSearchApiResponse;
import co.elasticsearch.enterprisesearch.client.model.response.search.ResponseDocument;
import co.elasticsearch.enterprisesearch.client.model.response.search.SearchApiResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
class SearchApiImpl<T extends ResponseDocument> implements SearchApi<T>{

    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;
    private final Class<T> resultType;

    @Override
    public SearchApiResponse<T> search(String engineName, SearchRequest request ){
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + "/api/as/v1/engines/{engineName}/search")).newBuilder().setPathSegment(4, engineName).build();

        Request okRequest = new Request.Builder().url(url).post(ClientUtils.marshalPayload(objectMapper,request)).build();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, resultType);
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,type);
    }


    @Override
    public MultiSearchApiResponse<T> search(String engineName, MultiSearchRequest request) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + "/api/as/v1/engines/{engineName}/search")).newBuilder().setPathSegment(4, engineName).build();

        Request okRequest = new Request.Builder().url(url).post(ClientUtils.marshalPayload(objectMapper,request)).build();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, resultType);
        type = objectMapper.getTypeFactory().constructParametricType(MultiSearchApiResponse.class,type);
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,type);
    }
}
