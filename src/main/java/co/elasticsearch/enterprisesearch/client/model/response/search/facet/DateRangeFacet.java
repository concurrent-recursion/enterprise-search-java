package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.DateRange;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a date range facet
 */
@Getter
@Setter
@Accessors(chain = true)
public class DateRangeFacet implements Facet{
    /**
     * The facet name
     * @param name the name of the facet
     */
    private String name;
    private List<DateRange> data = new ArrayList<>();

    @Override
    public FacetType getType() {
        return FacetType.RANGE;
    }

}
