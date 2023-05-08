package co.elasticsearch.enterprisesearch.client.model.response.search;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a text field in a search response document, that can contain multiple values
 */
@Getter
@Setter
@Accessors(chain = true)
public class TextArrayField implements Field {
    /**
     * The raw text array values of the field
     * @param raw the raw values
     * @return the raw values
     */
    private List<String> raw = new ArrayList<>();
    /**
     * The search snippet
     * @param snippet the snippet
     * @return the snippet
     */
    private String snippet;
}
