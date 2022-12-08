package co.elasticsearch.enterprisesearch.client.model.request.facet;

import co.elasticsearch.enterprisesearch.client.model.Sort;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Getter
@Setter
@Accessors(chain = true)
@JsonPropertyOrder({"name", "sort", "size"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
@JsonIgnoreProperties({"type"})
public class ValueFacet implements Facet {

    public ValueFacet() {
    }

    public ValueFacet(String fieldName) {
        this.fieldName = fieldName;
    }


    private String fieldName;

    public String getType() {
        return "value";
    }

    private String name;
    @Min(1)
    private Integer size;
    private Sort sort;
}
