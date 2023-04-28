package co.elasticsearch.enterprisesearch.client.model.response.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public abstract class ResponseDocument {
    /**
     * Metadata about this search result
     * @param meta the document metadata
     * @return the document metadata
     */
    @JsonProperty("meta")
    private MetaField meta;


}
