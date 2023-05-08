package co.elasticsearch.enterprisesearch.client.model.response.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Represents a search response document. This class is used to map the _meta fields onto the search results document
 */
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
