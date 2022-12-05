package co.elasticsearch.enterprisesearch.client.api;

import co.elasticsearch.enterprisesearch.client.model.DeleteDocumentsApiResponse;
import co.elasticsearch.enterprisesearch.client.model.IndexDocumentsApiResponse;
import co.elasticsearch.enterprisesearch.client.model.ListDocumentsApiResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface DocumentsApi<T> {

    @RequestLine("GET /api/as/v1/engines/{engineName}/documents/list")
    @Headers("Accept: application/json")
    ListDocumentsApiResponse<T> listDocuments(@Param("engineName") String engineName, @Param(value = "page[size]") Integer pageSize, @Param(value = "page[current]") Integer pageNumber);

    @RequestLine("GET /api/as/v1/engines/{engineName}/documents")
    @Headers("Accept: application/json")
    List<T> getDocuments(@Param("engineName") String engineName, @Param(value = "ids[]") List<String> ids);

    @RequestLine(value = "POST /api/as/v1/engines/{engineName}/documents")
    @Headers("Accept: application/json")
    List<IndexDocumentsApiResponse> indexDocuments(@Param("engineName") String engineName, List<T> documents);

    @RequestLine(value="DELETE /api/as/v1/engines/{engineName}/documents")
    @Headers("Accept: application/json")
    List<DeleteDocumentsApiResponse> deleteDocuments(@Param("engineName") String engineName,List<String> ids);

}
