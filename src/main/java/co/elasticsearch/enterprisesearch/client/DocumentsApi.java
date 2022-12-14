package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.response.documents.DeleteResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.ListResponse;

import java.util.List;

public interface DocumentsApi<T> {
    /**
     * Create or Update document(s) in the index
     * @param engineName The engine name
     * @param documents The documents to insert or update
     * @return The index response
     */
    IndexResponse index(String engineName, List<T> documents);

    /**
     * Retrieves one or more documents by id. All field values are returned in string format
     * @param engineName the engine name
     * @param ids the document ids
     * @return a list of documents matching the given ids. Documents not found will be null entries
     */
    List<T> getDocuments(String engineName, List<String> ids);

    /**
     * Delete the documents with the given ids
     * @param engineName The engine name
     * @param ids A list of document ids to delete
     * @return The delete response
     */
    DeleteResponse delete(String engineName, List<String> ids);

    /**
     * Lists documents in the given engine
     * @param engineName The name of the engine
     * @param page A page object
     * @return A list of documents
     */
    ListResponse<T> listDocuments(String engineName, Page page);
}
