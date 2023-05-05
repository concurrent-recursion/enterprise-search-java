package co.elasticsearch.enterprisesearch.client.model.response.search;

import co.elasticsearch.enterprisesearch.client.model.response.ErrorableResponse;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.Facet;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchApiResponse<T extends ResponseDocument> implements Iterable<T>, ErrorableResponse {
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
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<T> results = new ArrayList<>();

    /**
     * The errors associated with this response, if any
     * @param errors the errors
     * @return the errors
     */
    private List<String> errors = new ArrayList<>();

    /**
     * Value and Range facets for the query
     * @param facetMap The facets to build on the search results
     * @return the facetMap of facets
     */
    @JsonProperty("facets")
    @Getter(AccessLevel.PACKAGE)
    @JsonFormat(without = {JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    private Map<String, List<Facet>> facetMap = new LinkedHashMap<>();

    /**
     * Get the facets
     * @return The facets
     */
    @JsonIgnore
    @JsonSerialize
    public List<Facet> getFacets() {
        return facetMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @JsonIgnore
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return results.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        results.forEach(action);
    }

    @JsonIgnore
    @Override
    public Spliterator<T> spliterator() {
        return results.spliterator();
    }

    /**
     * Returns if there is 1 or more warnings for this response
     * @return true if there is at least 1 warning, otherwise false
     */
    @JsonIgnore
    public boolean isWarning(){
        return !meta.getWarnings().isEmpty();
    }

    /**
     * Returns if there is 1 or more alerts for this response
     * @return true if there is at least 1 alert, otherwise false
     */
    @JsonIgnore
    public boolean isAlert(){
        return !meta.getAlerts().isEmpty();
    }

    @JsonIgnore
    @Override
    public boolean isError() {
        return !errors.isEmpty();
    }
}
