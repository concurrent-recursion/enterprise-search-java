package co.elasticsearch.enterprisesearch.client.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Getter
@Setter
@Accessors(chain = true)
public class ResultFieldRendered {
    @Min(20)
    private Integer size;

    /**
     * If true, return the raw text field if no snippet is found. If false, only use snippets.
     * @param fallback true to return the raw text field if no snippet is found. otherwise, only use snippets.
     */
    private Boolean fallback;
}
