package co.elasticsearch.enterprisesearch.client.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResultField {
    /**
     * An exact representation of the value within a field. And it is exact! It is not HTML escaped.
     * @param raw The raw field
     */
    private ResultFieldRendered raw;
    /**
     * A snippet is an HTML escaped representation of the value within a field, where query matches are captured in <tt>&lt;em&gt;</tt> tags.
     * @param snippet The snippet field
     */
    private ResultFieldRendered snippet;
}
