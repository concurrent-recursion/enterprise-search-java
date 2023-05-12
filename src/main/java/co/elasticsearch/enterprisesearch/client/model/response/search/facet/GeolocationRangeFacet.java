package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Geolocation range facet
 */
@Getter
@Setter
@Accessors(chain = true)
public class GeolocationRangeFacet implements Facet{
    /**
     * The facet name
     * @param name the name of the facet
     * @return the name of the facet
     */
    private String name;
    /**
     * The facet values for this facet
     * @param data the values
     * @return the values
     */
    private List<FacetValue> data = new ArrayList<>();

    @Override
    public FacetType getType() {
        return FacetType.RANGE;
    }


}
