package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.search.MultiSearchRequest;
import co.elasticsearch.enterprisesearch.client.model.request.search.SearchRequest;
import co.elasticsearch.enterprisesearch.client.model.response.search.MultiSearchApiResponse;
import co.elasticsearch.enterprisesearch.client.model.response.search.ResponseDocument;
import co.elasticsearch.enterprisesearch.client.model.response.search.SearchApiResponse;

/**
 * Represents search operations for a particular engine
 * @param <T> The type of documents contained in the engine
 */
public interface SearchApi<T extends ResponseDocument> {
    /**
     * Executes a search
     * @param engineName The name of the engine in AppSearch
     * @param request The search request parameters
     * @return A SearchApiResponse for the request
     */
     SearchApiResponse<T> search(String engineName, SearchRequest request);

    /**
     * Executes a multi-search request
     * @param engineName The name of the engine to execute the search against
     * @paam request The multi-search request, containing one or more search requests
     * @return
     */
     MultiSearchApiResponse<T> search(String engineName, MultiSearchRequest request);
}
