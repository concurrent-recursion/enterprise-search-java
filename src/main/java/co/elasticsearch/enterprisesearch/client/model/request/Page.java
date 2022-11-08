package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Object to delimit the pagination parameters.
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"size","current"})
public class Page {
    /**
     * Number of results per page
     * @param size Must be greater than or equal to 1 and less than or equal to 1000.
     */
    @Min(1)
    @Max(1000)
    private Integer size = 10;
    /**
     * Page number of results to return, defaults to 1
     * @param current Must be greater than or equal to 1 and less than or equal to 100
     */
    @Min(1)
    @Max(100)
    private Integer current = 1;

}
