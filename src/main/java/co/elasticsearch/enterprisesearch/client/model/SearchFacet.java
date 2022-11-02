package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchFacet {
    private String type;
    private String name;
    private Integer size;
    private Sort sort;
}
