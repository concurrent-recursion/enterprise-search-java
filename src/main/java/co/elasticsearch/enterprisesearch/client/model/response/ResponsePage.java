package co.elasticsearch.enterprisesearch.client.model.response;

import co.elasticsearch.enterprisesearch.client.model.request.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"total_pages", "size", "current", "total_results"})
@ToString
public class ResponsePage extends Page {
    /**
     * Response Value<br>
     * Number representing the total pages of results. Value is 0 when you paginate beyond 10,000 results.
     *
     * @param totalPages The total number of pages
     * @return the total number of pages
     */
    @JsonProperty("total_pages")
    private Integer totalPages;

    /**
     * Response Value<br>
     * Number representing the total results across all pages.<br>
     * The values 0 through 9999 are exact counts.<br>
     * The value 10000 is a pseudo keyword representing greater than or equal to 10,000 results.
     *
     * @param totalResults The total number of results
     * @return the total number of results
     */
    @JsonProperty("total_results")
    private Integer totalResults;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponsePage)) return false;
        if (!super.equals(o)) return false;
        ResponsePage page = (ResponsePage) o;
        return Objects.equals(totalPages, page.totalPages) && Objects.equals(totalResults, page.totalResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), totalPages, totalResults);
    }
}
