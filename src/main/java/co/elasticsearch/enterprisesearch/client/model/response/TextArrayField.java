package co.elasticsearch.enterprisesearch.client.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

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
