package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.querysuggestions.QuerySuggestionsRequest;
import co.elasticsearch.enterprisesearch.client.model.response.querysuggestions.QuerySuggestionsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
class QuerySuggestionsApiImpl implements QuerySuggestionsApi{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;

    private static final String QUERY_SUGGESTIONS_PATH = "/api/as/v1/engines/{engineName}/query_suggestion";
    @Override
    public QuerySuggestionsResponse getQuerySuggestion(String engine, QuerySuggestionsRequest request) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + QUERY_SUGGESTIONS_PATH)).newBuilder().setPathSegment(4, engine).build();

        log.debug("Getting QuerySuggestions for {} , request:{}",url, request);
        Request okRequest = new Request.Builder()
                .url(url)
                .post(ClientUtils.marshalPayload(objectMapper,request))
                .build();
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,QuerySuggestionsResponse.class);
    }
}
