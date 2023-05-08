package co.elasticsearch.enterprisesearch.client.model.response.documents;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * Represents the result of a document delete operation
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class DeleteResult {
    /**
     * The id of the deleted document
     *
     * @param id the document id
     * @return the document id
     */
    private String id;
    /**
     * Whether the document was successfully deleted
     *
     * @param deleted whether the document was deleted
     * @return true if the document was deleted, otherwise false
     */
    private boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleteResult)) return false;
        DeleteResult that = (DeleteResult) o;
        return deleted == that.deleted && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deleted);
    }
}
