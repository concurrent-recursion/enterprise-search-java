package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * Represents an empty range facet, this exists because we cannot tell the type of a facet with no ranges
 */
@Getter
@Setter
@Accessors(chain = true)
public class EmptyRangeFacet implements Facet {
    /**
     * The name of the facet
     *
     * @param name the name of the facet
     * @return the name of the facet
     */
    private String name;

    @Override
    public FacetType getType() {
        return FacetType.RANGE;
    }

    @Override
    public List<FacetValue> getData() {
        return Collections.emptyList();
    }
}