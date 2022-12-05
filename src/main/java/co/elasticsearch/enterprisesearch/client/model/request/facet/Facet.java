package co.elasticsearch.enterprisesearch.client.model.request.facet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(using = FacetDeserializer.class)
public interface Facet {

    //TODO: any, all, none
    //TODO: Nested

}
