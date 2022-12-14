package co.elasticsearch.enterprisesearch.client.model.response.engines;

import co.elasticsearch.enterprisesearch.client.model.response.ResponsePage;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Meta {
    /**
     * Object to delimit the pagination parameters.
     * @param page The page parameters
     * @return the page
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = ResponsePage.class)
    private ResponsePage page;
}
