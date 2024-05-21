package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.TextValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a text value facet
 */
@Setter
@Getter
@Accessors(chain = true)
public class TextValueFacet implements Facet {
    /**
     * The facet name
     *
     * @param name the name of the facet
     * @return the name of the facet
     */
    private String name;

    /**
     * The facet values
     * @param data the value of the facet
     * @return the value of the facet
     */
    private List<FacetValue> data = new ArrayList<>();

    @Override
    public FacetType getType() {
        return FacetType.VALUE;
    }

    /**
     * The facet values
     * @param data The facet values
     * @return this facet
     */
    public TextValueFacet setData(List<TextValue> data){
        this.data.addAll(data);
        return this;
    }
}
