package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.client.model.Sort;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"field", "size", "sort", "collapse"})
public class Group {
    /**
     * Field name to group results on.
     *
     * @param field Field name to group results on.
     */
    @NotNull
    private String field;
    /**
     * Number of results to be included in the _group key of the returned document. Can be between 1 and 10. Defaults to 10.
     *
     * @param size number of results to be included in the _group key
     */
    @Min(1)
    @Max(10)
    private Integer size;
    /**
     * A JSON object containing the field name or _score as the key and the value as asc or desc. The default sort is by descending relevance.
     *
     * @param sort a sort field
     */
    private Sort sort;
    /**
     * Experimental. Provides Paging and Sorting support. The field you are grouping on must not have multiple values. Multiple values will cause an error.
     *
     * @param collapse Whether to enable paging and sorting
     */
    private Boolean collapse;
}
