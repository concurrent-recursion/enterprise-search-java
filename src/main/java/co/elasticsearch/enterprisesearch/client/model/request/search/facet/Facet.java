package co.elasticsearch.enterprisesearch.client.model.request.search.facet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents a facet in the search request
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(using = FacetDeserializer.class)
public interface Facet {
    /**
     * The field from your schema upon which to apply your facet.
     * @return The field name
     */
    @JsonIgnore
    String getFieldName();

    /**
     * Set the field upon which to apply your facet.
     * @param fieldName The name of the field
     * @return The Facet
     */
    Facet setFieldName(String fieldName);

    /**
     * Type of facet
     * @return The facet type
     */

    String getType();

    /**
     * Name given to the facet
     * @return the facet name
     */
    String getName();

}
