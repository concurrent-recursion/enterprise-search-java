package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class EmptyRangeFacet  implements Facet{
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