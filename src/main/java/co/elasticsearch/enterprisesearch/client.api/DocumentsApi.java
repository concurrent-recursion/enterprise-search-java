package co.elasticsearch.enterprisesearch.client.api;

import co.elasticsearch.enterprisesearch.client.model.DeleteDocumentsApiResponse;
import co.elasticsearch.enterprisesearch.client.model.IndexDocumentsApiResponse;
import co.elasticsearch.enterprisesearch.client.model.ListDocumentsApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="es-documents",url = "${enterprisesearch.base-uri}/api/as/v1/engines/")
interface DocumentsApi<T> {
    @GetMapping(value = "/{engineName}/documents/list",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ListDocumentsApiResponse<T>> listDocuments(@PathVariable("engineName") String engineName, @RequestParam(value = "page[size]",required = false,defaultValue = "10") Integer pageSize, @RequestParam(value = "page[current]",required = false, defaultValue = "1") Integer pageNumber);

    @GetMapping(value = "/{engineName}/documents",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<T>> getDocuments(@PathVariable("engineName") String engineName, @RequestParam(value = "ids[]",required = false) List<String> ids);

    @GetMapping(value = "/{engineName}/documents",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<IndexDocumentsApiResponse>> indexDocuments(@PathVariable("engineName") String engineName, @RequestBody List<T> documents);

    @DeleteMapping(value="/{engineName}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DeleteDocumentsApiResponse>> deleteDocuments(@PathVariable("engineName") String engineName,@RequestBody List<String> ids);

}
