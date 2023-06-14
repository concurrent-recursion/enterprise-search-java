package co.elasticsearch.enterprisesearch.client.model.request.querysuggestions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a QuerySuggestion request document
 */
@Getter
@Setter
@Accessors(chain = true)
public class Document {
    /**
     * The fields contained in the document
     * @param fields the fields in the documents
     * @return the fields in the document
     */
    private List<String> fields = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(fields, document.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }
}
