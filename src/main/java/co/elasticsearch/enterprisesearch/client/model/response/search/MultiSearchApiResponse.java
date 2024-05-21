package co.elasticsearch.enterprisesearch.client.model.response.search;

import co.elasticsearch.enterprisesearch.client.model.response.ErrorableResponse;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Represents a multi-search response
 * @param <T> The type of document contained in the SearchApiResponses
 */
@Data
@Accessors(chain = true)
public class MultiSearchApiResponse<T extends ResponseDocument> implements Iterable<SearchApiResponse<T>>, ErrorableResponse {

    /**
     * Create MultiSearchApiResponse object
     * @param results The list of Search Results from the response
     */
    @JsonCreator
    public MultiSearchApiResponse(List<SearchApiResponse<T>> results){
        this.results = results;
    }

    /**
     * The results
     * @param results the results
     * @return the results
     */
    @JsonValue
    private final List<SearchApiResponse<T>> results;

    @Override
    public boolean isError() {
        return results.stream().anyMatch(SearchApiResponse::isError);
    }

    @NotNull
    @Override
    public Iterator<SearchApiResponse<T>> iterator() {
        return results.iterator();
    }

    @Override
    public void forEach(Consumer<? super SearchApiResponse<T>> action) {
        results.forEach(action);
    }

    @Override
    public Spliterator<SearchApiResponse<T>> spliterator() {
        return results.spliterator();
    }
}
