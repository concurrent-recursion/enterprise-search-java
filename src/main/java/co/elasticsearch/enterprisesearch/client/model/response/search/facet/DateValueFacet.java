package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.DateValue;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a date value facet
 */
@Getter
@Setter
@Accessors(chain = true)
public class DateValueFacet implements Facet{
    /**
     * The facet name
     * @param name the name of the facet
     */
    private String name;
    private List<DateValue> data = new ArrayList<>();

    @Override
    public FacetType getType() {
        return FacetType.VALUE;
    }

}
