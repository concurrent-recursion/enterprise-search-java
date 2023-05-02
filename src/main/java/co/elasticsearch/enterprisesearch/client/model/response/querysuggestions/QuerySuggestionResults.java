package co.elasticsearch.enterprisesearch.client.model.response.querysuggestions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the Query Suggestion Results
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class QuerySuggestionResults {
    /**
     * A wrapper for the suggestions
     * @param suggestions the suggestions
     * @return the suggestions
     */
    @JsonProperty("documents")
    private List<QuerySuggestion> suggestions = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuerySuggestionResults)) return false;
        QuerySuggestionResults that = (QuerySuggestionResults) o;
        return Objects.equals(suggestions, that.suggestions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suggestions);
    }
}
