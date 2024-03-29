package co.elasticsearch.enterprisesearch.client.model.response.documents;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * Represents a Response from List Document Request
 * @param <T> The type of documents contained in the response
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class ListResponse<T>  implements Iterable<T>{
    /**
     * The documents from the given engine
     * @param documents the result documents
     * @return the result documents
     */
    @JsonProperty("results")
    private List<T> documents = new ArrayList<>();

    /**
     * The list metadata
     * @param meta the response metadata
     * @return the response metadata
     */
    private Meta meta;


    @NotNull
    @Override
    public Iterator<T> iterator() {
        return documents.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        documents.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return documents.spliterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListResponse)) return false;
        ListResponse<?> that = (ListResponse<?>) o;
        return documents.equals(that.documents) && meta.equals(that.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documents, meta);
    }
}
