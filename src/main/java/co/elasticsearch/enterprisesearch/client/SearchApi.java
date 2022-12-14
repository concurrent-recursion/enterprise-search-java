package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.search.SearchApiRequest;
import co.elasticsearch.enterprisesearch.client.model.response.search.ResponseDocument;
import co.elasticsearch.enterprisesearch.client.model.response.search.SearchApiResponse;

public interface SearchApi<T extends ResponseDocument> {
    /**
     * Executes a search
     * @param engineName The name of the engine in AppSearch
     * @param request The search request parameters
     * @return A SearchApiResponse for the request
     */
     SearchApiResponse<T> search(String engineName, SearchApiRequest request);
}
