package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.querysuggestions.QuerySuggestionsRequest;
import co.elasticsearch.enterprisesearch.client.model.response.querysuggestions.QuerySuggestionsResponse;
import co.elasticsearch.enterprisesearch.client.model.response.sourceengines.SourceEnginesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

import static co.elasticsearch.enterprisesearch.client.AppSearchClient.APP_JSON;

@Slf4j
@RequiredArgsConstructor
public class QuerySuggestionsApiImpl implements QuerySuggestionsApi{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;

    private static final String QUERY_SUGGESTIONS_PATH = "/api/as/v1/engines/{engineName}/query_suggestion";
    @Override
    public QuerySuggestionsResponse getQuerySuggestion(String engine, QuerySuggestionsRequest request) throws IOException{
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + QUERY_SUGGESTIONS_PATH)).newBuilder().setPathSegment(4, engine).build();

        log.debug("Adding meta engines {} , request:{}",url, request);
        Request okRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create(objectMapper.writeValueAsBytes(engine), APP_JSON))
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(), QuerySuggestionsResponse.class);
        }
    }
}
