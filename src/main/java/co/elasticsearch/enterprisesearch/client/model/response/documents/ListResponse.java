package co.elasticsearch.enterprisesearch.client.model.response.documents;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Getter
@Setter
@Accessors(chain = true)
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
}
