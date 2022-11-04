package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Object to delimit the pagination parameters.
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
    /**
     * Response Value<br>
     * Number representing the total pages of results. Value is 0 when you paginate beyond 10,000 results.
     * @param totalPages The total number of pages
     */
    @JsonProperty("total_pages")
    private Integer totalPages;

    /**
     * Response Value<br>
     * Number representing the total results across all pages.<br>
     * The values 0 through 9999 are exact counts.<br>
     * The value 10000 is a pseudo keyword representing greater than or equal to 10,000 results.
     * @param totalResults The total number of results
     */
    @JsonProperty("total_results")
    private Integer totalResults;
}
