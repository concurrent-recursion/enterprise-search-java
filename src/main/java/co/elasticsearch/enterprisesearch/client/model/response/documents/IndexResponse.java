package co.elasticsearch.enterprisesearch.client.model.response.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class IndexResponse implements Iterable<IndexResponse.IndexResult>{
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
        return documents.stream().anyMatch(r -> !r.errors.isEmpty());
    }

    /**
     * Gets a List of IndexResults that have an error
     * @return A list of IndexResult objects that have an error
     */
    public List<IndexResult> getErrors(){
        return documents.stream().filter(r -> r.errors.isEmpty()).collect(Collectors.toList());
    }

    /**
     * Get a set containing all the ids returned by the index request
     * @return The ids from the IndexResult objects
     */
    public Set<String> getIds(){
        return documents.stream().map(IndexResult::getId).collect(Collectors.toSet());
    }


    @Getter
    @Setter
    @Accessors(chain = true)
    public static class IndexResult {


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
    }
}
