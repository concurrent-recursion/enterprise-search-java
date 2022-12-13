package co.elasticsearch.enterprisesearch.client.model.request.querysuggestions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class QuerySuggestionsRequest {
    private String query;
    private SuggestionTypes types = new SuggestionTypes();
    private Integer size;

}
