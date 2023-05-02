package co.elasticsearch.enterprisesearch.client.model.response.documents;

import co.elasticsearch.enterprisesearch.client.model.response.ErrorableResponse;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents the response from an IndexRequest
 */
@Data
@Accessors(chain = true)
@ToString
public class IndexResponse implements Iterable<IndexResult>, ErrorableResponse {
    /**
     * The results of the index operation
     * @param documents the results
     * @return the results
     */
    @JsonValue
    private List<IndexResult> documents = new ArrayList<>();

    @NotNull
    @Override
    public Iterator<IndexResult> iterator() {
        return documents.iterator();
    }

    @Override
    public Spliterator<IndexResult> spliterator() {
        return documents.spliterator();
    }

    @Override
    public void forEach(Consumer<? super IndexResult> action) {
        documents.forEach(action);
    }

    /**
     * Checks if any of the results contain an error during document indexing
     * @return true if there is an IndexResult with an error, otherwise false
     */
    public boolean isError(){
        return documents.stream().anyMatch(r -> !r.getErrors().isEmpty());
    }

    /**
     * Gets a List of IndexResults that have an error
     * @return A list of IndexResult objects that have an error
     */
    public List<IndexResult> getErrors(){
        return documents.stream().filter(r -> !r.getErrors().isEmpty()).collect(Collectors.toList());
    }

    /**
     * Get a set containing all the ids returned by the index request
     * @return The ids from the IndexResult objects
     */
    public Set<String> getIds(){
        return documents.stream().map(IndexResult::getId).collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndexResponse)) return false;
        IndexResponse response = (IndexResponse) o;
        return Objects.equals(documents, response.documents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documents);
    }
}