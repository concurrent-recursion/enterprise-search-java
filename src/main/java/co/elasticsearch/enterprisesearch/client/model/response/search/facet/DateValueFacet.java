package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.DateValue;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DateValueFacet implements Facet{
    private String name;
    private List<FacetValue> data;

    @Override
    public FacetType getType() {
        return FacetType.VALUE;
    }

    DateValueFacet setData(List<DateValue> data){
        this.data = new ArrayList<>();
        this.data.addAll(data);
        return this;
    }
}
