package co.elasticsearch.enterprisesearch.client.model.response.search;

import co.elasticsearch.enterprisesearch.client.model.response.ErrorableResponse;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents the response from a search operation
 * @param <T> The type of document contained in the given engine
 */
@Data
@Accessors(chain = true)
@Slf4j
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

    /**
     * Get a list of Facets that are mapped to a specific field
     * @param fieldName The name of the field in the search document
     * @return A list of facets
     */
    public List<Facet> getFacetsByField(String fieldName){
        return facetMap.getOrDefault(fieldName,Collections.emptyList());
    }

    /**
     * Get the Facet on the specified field, with the specified name, and type. The facet
     * @param facetName The name of the facet
     * @param facetType the type of the facet
     * @return The facet if one is found, and is the correct class, or an empty Optional
     * @param <F> The type of facet expected. If the type does not match, it will return an empty Optional
     */
    public <F extends Facet> Optional<F> getFacetByName(String facetName,Class<F> facetType){
        return facetMap.values().stream()
                .flatMap(Collection::stream)
                .filter(f -> facetName.equals(f.getName()))
                .map(f -> castFacet(f,facetType))
                .filter(Objects::nonNull)
                .findFirst();
    }

    /**
     * Get the Facet on the specified field, with the specified name, and type. The facet
     * @param fieldName The name of the field in the search document
     * @param facetName The name of the facet
     * @param facetType The type of the facet
     * @return The facet if one is found, and is the correct class, or an empty Optional
     * @param <F> The type of facet expected. If the type does not match, it will return an empty Optional
     */

    public <F extends Facet> Optional<F> getFacetByFieldAndName(String fieldName,String facetName,Class<F> facetType){
        return getFacetsByField(fieldName).stream()
                .filter(f -> facetName.equals(f.getName()))
                .map(f -> castFacet(f,facetType))
                .filter(Objects::nonNull)
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    private static <F extends Facet> F castFacet(Facet f, Class<F> facetType){
        if(f.getClass().equals(facetType)){
            return facetType.cast(f);
        }else if(f.getClass().equals(EmptyValueFacet.class)){
            if(facetType.equals(TextValueFacet.class)){
                return (F) new TextValueFacet().setName(f.getName());
            } else if(facetType.equals(NumberValueFacet.class)){
                return (F) new NumberValueFacet().setName(f.getName());
            } else if(facetType.equals(DateValueFacet.class)){
                return (F) new DateValueFacet().setName(f.getName());
            }else{
                log.warn("Requested Facet Type {} but was {}",facetType, f.getClass());
                return null;
            }
        } else if (f.getClass().equals(EmptyRangeFacet.class)) {
            if(facetType.equals(NumberRangeFacet.class)){
                return (F) new NumberRangeFacet().setName(f.getName());
            } else if(facetType.equals(DateRangeFacet.class)){
                return (F) new DateRangeFacet().setName(f.getName());
            } else if(facetType.equals(GeolocationRangeFacet.class)){
                return (F) new GeolocationRangeFacet().setName(f.getName());
            }else{
                log.warn("Requested Facet Type {} but was {}",facetType, f.getClass());
                return null;
            }
        }
        return null;
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
