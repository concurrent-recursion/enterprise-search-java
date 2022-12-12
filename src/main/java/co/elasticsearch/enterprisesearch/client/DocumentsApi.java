package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.response.documents.DeleteResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.ListResponse;

import java.io.IOException;
import java.util.List;

public interface DocumentsApi<T> {
    /**
     * Create or Update document(s) in the index
     * @param engineName The engine name
     * @param documents The documents to insert or update
     * @return The index response
     * @throws IOException
     */
    IndexResponse index(String engineName, List<T> documents) throws IOException;

    /**
     * Delete the documents with the given ids
     * @param engineName The engine name
     * @param ids A list of document ids to delete
     * @return The delete response
     * @throws IOException
     */
    DeleteResponse delete(String engineName, List<String> ids) throws IOException;

    /**
     * Lists documents in the given engine
     * @param engineName The name of the engine
     * @param page A page object
     * @return A list of documents
     */
    ListResponse<T> listDocuments(String engineName, Page page);
}
