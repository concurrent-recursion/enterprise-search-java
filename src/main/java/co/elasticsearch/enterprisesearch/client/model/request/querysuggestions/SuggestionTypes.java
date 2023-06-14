package co.elasticsearch.enterprisesearch.client.model.request.querysuggestions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * Represents a suggestion type
 */
@Getter
@Setter
@Accessors(chain = true)
public class SuggestionTypes {
    /**
     * Represents a document in the results
     * @param documents the documents
     * @return the documents
     */
    private Document documents = new Document();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuggestionTypes that = (SuggestionTypes) o;
        return Objects.equals(documents, that.documents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documents);
    }
}
