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
     *
     * @param value the value of the facet
     * @return the value of the facet
     */
    private String value;
    /**
     * The count of the facet value
     *
     * @param count the count of the documents matching the value
     * @return he count of the documents matching the value
     */
    private Long count;
}
