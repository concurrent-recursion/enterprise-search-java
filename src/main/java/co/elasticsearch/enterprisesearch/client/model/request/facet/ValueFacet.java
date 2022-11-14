package co.elasticsearch.enterprisesearch.client.model.request.facet;

import co.elasticsearch.enterprisesearch.client.model.Sort;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Getter
@Setter
@Accessors(chain = true)
@JsonPropertyOrder({"type","name","sort","size"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValueFacet implements Facet {
    private final String type = "value";
    private String name;
    @Min(1)
    private Integer size;
    private Sort sort;
}
