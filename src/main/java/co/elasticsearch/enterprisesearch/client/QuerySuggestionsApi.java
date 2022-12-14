package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.querysuggestions.QuerySuggestionsRequest;
import co.elasticsearch.enterprisesearch.client.model.response.querysuggestions.QuerySuggestionsResponse;

public interface QuerySuggestionsApi {
    QuerySuggestionsResponse getQuerySuggestion(String engine, QuerySuggestionsRequest request);
}
