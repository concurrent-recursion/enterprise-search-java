package co.elasticsearch.enterprisesearch.client.model.request.facet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(using = FacetDeserializer.class)
public interface Facet {
    @JsonIgnore
    String getFieldName();
    Facet setFieldName(String fieldName);
    String getType();
    String getName();

}
