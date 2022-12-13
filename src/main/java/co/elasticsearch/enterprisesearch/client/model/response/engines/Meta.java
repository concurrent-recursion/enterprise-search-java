package co.elasticsearch.enterprisesearch.client.model.response.engines;

import co.elasticsearch.enterprisesearch.client.model.response.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Meta {
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Page.class)
    private Page page;
}
