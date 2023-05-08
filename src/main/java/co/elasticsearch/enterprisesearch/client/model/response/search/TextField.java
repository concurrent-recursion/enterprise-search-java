package co.elasticsearch.enterprisesearch.client.model.response.search;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents a text field in a search response document, that only contains a single value
 */
@Getter
@Setter
@Accessors(chain = true)
public class TextField implements Field {
    /**
     * The raw text value of the field
     * @param raw the raw value
     * @return the raw value
     */
    private String raw;
    /**
     * The snippet of the field
     * @param snippet the snippet
     * @return the snippet
     */
    private String snippet;
}
