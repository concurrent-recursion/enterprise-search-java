package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.client.model.Sort;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Getter
@Setter
@Accessors(chain = true)
public class ValueFacet implements Facet{
    private String key;
    private final String type = "value";
    private String name;
    @Min(1)
    private Integer size;
    private Sort sort;
}
