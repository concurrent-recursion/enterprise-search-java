package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.request.search.facet.ValueFacet;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.TextValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;


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
     */
    private String name;

    private List<FacetValue> data = new ArrayList<>();

    @Override
    public FacetType getType() {
        return FacetType.VALUE;
    }
    public TextValueFacet setData(List<TextValue> data){
        this.data.addAll(data);
        return this;
    }
}
