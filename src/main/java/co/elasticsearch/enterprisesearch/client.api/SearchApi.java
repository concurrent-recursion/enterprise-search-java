package co.elasticsearch.enterprisesearch.client.api;

import co.elasticsearch.enterprisesearch.client.model.request.SearchApiRequest;
import co.elasticsearch.enterprisesearch.client.model.response.SearchApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "es-search",url = "${enterprisesearch.base-uri}/api/as/v1/engines")
interface SearchApi<T> {
    @PostMapping("/{engineName}/search")
    ResponseEntity<SearchApiResponse<T>> postSearch(@PathVariable("engineName") String engineName, @RequestBody SearchApiRequest request);

}
