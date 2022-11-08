package co.elasticsearch.enterprisesearch.client.model.request.facet;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface Facet {

    //TODO: any, all, none
    //TODO: Nested
}
