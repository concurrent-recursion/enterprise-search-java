package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.DateRange;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DateRangeFacet implements Facet{
    private String name;
    private List<FacetValue> data;

    @Override
    public FacetType getType() {
        return FacetType.RANGE;
    }

    DateRangeFacet setData(List<DateRange> data){
        this.data = new ArrayList<>();
        this.data.addAll(data);
        return this;
    }
}
