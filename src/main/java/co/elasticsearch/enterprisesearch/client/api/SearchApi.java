package co.elasticsearch.enterprisesearch.client.api;

import co.elasticsearch.enterprisesearch.client.model.request.SearchApiRequest;
import co.elasticsearch.enterprisesearch.client.model.response.SearchApiResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;


public interface SearchApi<T> {
    @RequestLine("POST /api/as/v1/engines/{engineName}/search")
    @Headers("Accept: application/json")
    SearchApiResponse<T> postSearch(@Param("engineName") String engineName, SearchApiRequest request);
}
