package co.elasticsearch.enterprisesearch.client.model.request.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a multi-search request to the search api
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MultiSearchRequest {
    /**
     * The search queries to execute
     * @param queries The query list
     * @return The query list
     */
    private List<SearchRequest> queries = new ArrayList<>();
}
