package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents the Facet returned in a search response
 */
@JsonDeserialize(using = FacetDeserializer.class)
@JsonPropertyOrder({"type","name","data"})
public interface Facet  {

    /**
     * Name given to the facet
     * @return the facet name
     */
    String getName();

    /**
     * Type of facet
     * @return The facet type
     */
    FacetType getType();

    /**
     * The results of the facet
     * @return the results of the facet
     */
    List<FacetValue> getData();

}
