package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.querysuggestions.QuerySuggestionsRequest;
import co.elasticsearch.enterprisesearch.client.model.response.querysuggestions.QuerySuggestionsResponse;

/**
 * Represents the operations on the Query Suggestions API
 */
public interface QuerySuggestionsApi {
    /**
     * Gets query suggestions for the given engine and request
     * @param engine The engine name
     * @param request The query suggestion request
     * @return The response
     */
    QuerySuggestionsResponse getQuerySuggestion(String engine, QuerySuggestionsRequest request);
}
