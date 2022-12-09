package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

/**
 * Object to delimit the pagination parameters.
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"size", "current"})
public class Page {
    /**
     * Number of results per page
     *
     * @param size Must be greater than or equal to 1 and less than or equal to 1000.
     * @return page size
     */
    @Min(1)
    @Max(1000)
    private Integer size = 10;
    /**
     * Page number of results to return, defaults to 1
     *
     * @param current Must be greater than or equal to 1 and less than or equal to 100
     * @return current page number
     */
    @Min(1)
    @Max(100)
    private Integer current = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;
        Page page = (Page) o;
        return Objects.equals(size, page.size) && Objects.equals(current, page.current);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, current);
    }
}
