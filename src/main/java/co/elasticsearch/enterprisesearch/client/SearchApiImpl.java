package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.SearchApiRequest;
import co.elasticsearch.enterprisesearch.client.model.response.search.ResponseDocument;
import co.elasticsearch.enterprisesearch.client.model.response.search.SearchApiResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

import static co.elasticsearch.enterprisesearch.client.AppSearchClient.APP_JSON;

@RequiredArgsConstructor
@Slf4j
public class SearchApiImpl<T extends ResponseDocument> implements SearchApi<T>{

    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;
    private final Class<T> resultType;

    @Override
    public SearchApiResponse<T> search(String engineName,SearchApiRequest request ) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + "/api/as/v1/engines/{engineName}/search")).newBuilder().setPathSegment(4, engineName).build();
        log.debug("Executing AppSearch Search url:{}", url);
        Request okRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create(objectMapper.writeValueAsBytes(request), APP_JSON))
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, resultType);
            return objectMapper.readValue(response.body().byteStream(), type);
        }
    }


}
