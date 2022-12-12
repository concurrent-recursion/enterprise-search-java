package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.SearchApiRequest;
import co.elasticsearch.enterprisesearch.client.model.response.search.ResponseDocument;
import co.elasticsearch.enterprisesearch.client.model.response.search.SearchApiResponse;

import java.io.IOException;

public interface SearchApi<T extends ResponseDocument> {
    /**
     * Executes a search
     * @param engineName The name of the engine in AppSearch
     * @param request The search request parameters
     * @return A SearchApiResponse for the request
     * @throws IOException
     */
     SearchApiResponse<T> search(String engineName, SearchApiRequest request) throws IOException;
}
