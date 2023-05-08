package co.elasticsearch.enterprisesearch.client.model.response.querysuggestions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * Represents a response from the query suggestions operation
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class QuerySuggestionsResponse {
    /**
     * Metadata about the query request
     * @param meta the metadata
     * @return the metadata
     */
    private Meta meta = new Meta();
    /**
     * The results
     * @param results the results
     * @return the results
     */
    private QuerySuggestionResults results = new QuerySuggestionResults();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuerySuggestionsResponse)) return false;
        QuerySuggestionsResponse that = (QuerySuggestionsResponse) o;
        return Objects.equals(meta, that.meta) && Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meta, results);
    }
}
