package co.elasticsearch.enterprisesearch.client.model.response.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchApiResponse<T extends ResponseDocument> implements Iterable<T>{
    /**
     * Object delimiting the results meta data.
     * @param meta the page metadata
     * @return the page metadata
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Meta.class)
    private Meta meta = new Meta();

    /**
     * The documents returned by the request
     * @param results the results
     * @return the results
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    private List<T> results = new ArrayList<>();

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return results.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        results.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return results.spliterator();
    }
}
