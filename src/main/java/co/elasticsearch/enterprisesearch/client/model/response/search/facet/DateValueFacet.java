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
     * @return The name of the facet
     */
    private String name;
    /**
     * The DateValueFacet values that represent this facet
     * @param data a list of DateValueFacets
     * @return a list of DateValueFacets
     */
    private List<FacetValue> data = new ArrayList<>();

    @Override
    public FacetType getType() {
        return FacetType.VALUE;
    }

    /**
     * Set the DateValueFacets for this facet
     * @param dateValues The date values that represent the values of this facet
     * @return This facet
     */
    public DateValueFacet setData(List<DateValue> dateValues){
        data.addAll(dateValues);
        return this;
    }


}
