package co.elasticsearch.enterprisesearch.client.model.response.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a search response document. This class is used to map the _meta fields onto the search results document
 */
@Getter
public abstract class ResponseDocument {
    /**
     * Metadata about this search result
     *
     * @param meta the document metadata
     * @return the document metadata
     */
    @JsonProperty("_meta")
    private MetaField meta;

    /**
     * The _group documents related to this result
     * @return all other documents that share an identical value within the grouped field, or empty list if grouping
     * wasn't applied
     */
    @JsonProperty("_group")
    private List<ResponseDocument> groupedDocuments = new ArrayList<>();

    /**
     * The key used to group the result
     * @return the key used to group the result, or null if grouping wasn't applied
     */
    @JsonProperty("_group_key")
    private String groupKey;

}
