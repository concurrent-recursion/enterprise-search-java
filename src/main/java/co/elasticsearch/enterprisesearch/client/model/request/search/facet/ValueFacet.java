package co.elasticsearch.enterprisesearch.client.model.request.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.Sort;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@JsonPropertyOrder({"type","name", "sort", "size"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
public class ValueFacet implements Facet {

    public ValueFacet() {
    }

    public ValueFacet(String fieldName) {
        this.fieldName = fieldName;
    }


    private String fieldName;

    @Override
    public FacetType getType() {
        return FacetType.VALUE;
    }

    /**
     * Name given to facet
     * @param name The name
     * @return the name
     */
    private String name;
    /**
     * How many facets would you like to return? Can be between 1 and 250 (or the value of the app_search.engine.total_facet_values_returned.limit configuration setting). 10 facets is the default.
     * @param size The number of facets to return
     * @return The number of facets to return
     */
    @Min(1)
    private Integer size;
    /**
     * Sort field. Either count or value
     * @param sortField The sort field
     * @return The sort field
     */
    @JsonIgnore
    private FacetSortField sortField = FacetSortField.COUNT;
    /**
     * Sort Order. Either asc or desc
     * @param sortOrder the sort order
     * @return The sortOrder
     */
    @JsonIgnore
    private Sort.Order sortOrder = Sort.Order.DESCENDING;


    @JsonProperty("sort")
    Map<FacetSortField,Sort.Order> getSort(){
        return Map.of(sortField,sortOrder);
    }
}
