package co.elasticsearch.enterprisesearch.client.model.response.querysuggestions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class QuerySuggestionResults {
    @JsonProperty("documents")
    private List<QuerySuggestion> suggestions = new ArrayList<>();
}
