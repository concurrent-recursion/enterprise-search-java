package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.TextValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Accessors(chain = true)
public class TextValueFacet implements Facet{
    private String name;
    private List<FacetValue> data;

    @Override
    public FacetType getType() {
        return FacetType.VALUE;
    }

    TextValueFacet setData(List<TextValue> data){
        this.data = new ArrayList<>();
        this.data.addAll(data);
        return this;
    }

}
