package co.elasticsearch.enterprisesearch.client.model.response.search;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface Field {
    Object getRaw();
}
