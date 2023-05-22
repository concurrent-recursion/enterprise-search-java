package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
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
public class TextValueFacet implements Facet, Iterable<TextValue> {
    /**
     * The facet name
     *
     * @param name the name of the facet
     */
    private String name;

    private List<TextValue> data = new ArrayList<>();

    @Override
    public FacetType getType() {
        return FacetType.VALUE;
    }

    @NotNull
    @Override
    public Iterator<TextValue> iterator() {
        return this.data.iterator();
    }

    @Override
    public void forEach(Consumer<? super TextValue> action) {
        this.data.forEach(action);
    }

    @Override
    public Spliterator<TextValue> spliterator() {
        return this.data.spliterator();
    }
}
