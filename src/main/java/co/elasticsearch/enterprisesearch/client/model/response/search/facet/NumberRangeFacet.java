package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.NumberRange;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class NumberRangeFacet implements Facet{
    private String name;
    private List<FacetValue> data = new ArrayList<>();

    @Override
    public FacetType getType() {
        return FacetType.RANGE;
    }

    NumberRangeFacet setData(List<NumberRange> data){
        this.data = new ArrayList<>();
        this.data.addAll(data);
        return this;
    }

}
