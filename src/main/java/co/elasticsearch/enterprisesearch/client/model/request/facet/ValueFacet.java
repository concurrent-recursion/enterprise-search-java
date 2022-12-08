package co.elasticsearch.enterprisesearch.client.model.request.facet;

import co.elasticsearch.enterprisesearch.client.model.Sort;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Getter
@Setter
@Accessors(chain = true)
@JsonPropertyOrder({"type", "name", "sort", "size"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
public class ValueFacet implements Facet {

    public ValueFacet(@JsonProperty("name") String name){
        this.name = name;
    }
    private final String type = "value";
    private final String name;
    @Min(1)
    private Integer size;
    private Sort sort;
}
