package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents a value of the text facet
 */
@Getter
@Setter
@Accessors(chain = true)
public class TextValue implements FacetValueValue<String> {
    /**
     * The facet value
     * @return the value of the facet
     */
    private String value;
    /**
     * The count of the facet value
     * @return the count of the facet value in the engine
     */
    private Long count;
}
