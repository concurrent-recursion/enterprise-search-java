package co.elasticsearch.enterprisesearch.client.model.response.querysuggestions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class QuerySuggestionsResponse {
    private Meta meta;
    private QuerySuggestionResults results;
}
