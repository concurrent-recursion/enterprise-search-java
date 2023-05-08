package co.elasticsearch.enterprisesearch.client.model.response.querysuggestions;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * Represents a query suggestion response
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuerySuggestion {
    /**
     * The suggestion
     * @param suggestion the suggestion
     * @return the suggestion
     */
    private String suggestion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuerySuggestion)) return false;
        QuerySuggestion that = (QuerySuggestion) o;
        return Objects.equals(suggestion, that.suggestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suggestion);
    }
}
