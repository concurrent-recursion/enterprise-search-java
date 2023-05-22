package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * Represents an empty value facet, this exists because we cannot tell the type of a facet with no values
 */
@Getter
@Setter
@Accessors(chain = true)
public class EmptyValueFacet implements Facet{
    /**
     * The name of the facet
     * @param name the facet name
     * @return the facet name
     */
    private String name;

    @Override
    public FacetType getType() {
        return FacetType.VALUE;
    }

    @Override
    public List<FacetValue> getData() {
        return Collections.emptyList();
    }
}
