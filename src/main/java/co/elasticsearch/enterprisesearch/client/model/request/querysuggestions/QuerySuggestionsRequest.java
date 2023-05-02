package co.elasticsearch.enterprisesearch.client.model.request.querysuggestions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents a Query Suggestion Request
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuerySuggestionsRequest {
    /**
     * The query
     * @param query the query
     * @return the query
     */
    private String query;
    /**
     * The types of suggestions
     * @param types the suggestion types
     * @return the suggestion types
     */
    private SuggestionTypes types = new SuggestionTypes();
    /**
     * The number of suggestions to return
     * @param size the number of suggestions to return
     * @return the number of suggestions to return
     */
    private Integer size;

}
