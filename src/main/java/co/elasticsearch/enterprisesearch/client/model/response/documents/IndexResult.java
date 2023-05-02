package co.elasticsearch.enterprisesearch.client.model.response.documents;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an individual document in an IndexResponse
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class IndexResult {


    /**
     * The indexed document's id
     *
     * @param id the document id
     * @return the document id
     */
    private String id;
    /**
     * List of errors, if any, while indexing the document
     *
     * @param errors list of errors
     * @return list of errors
     */
    private List<String> errors = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndexResult)) return false;
        IndexResult that = (IndexResult) o;
        return id.equals(that.id) && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, errors);
    }
}
