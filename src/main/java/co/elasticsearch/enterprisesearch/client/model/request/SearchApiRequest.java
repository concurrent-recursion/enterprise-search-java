package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.client.model.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchApiRequest {

    /**
     * String or number to match.<br>
     * The value '' (empty string) matches all documents. (Note that API limits for documents returned applies.)<br>
     * The following Lucene query syntax is supported:
     * <ul>
     *     <li>double quoted strings</li>
     *     <li><tt>+</tt> and <tt>-</tt></li>
     *     <li><tt>AND</tt>, <tt>OR</tt>, <tt>NOT</tt></li>
     * </ul>
     * @param query String or number to match
     *
     */
    private String query;

    /**
     * Object to delimit the pagination parameters.
     * @param page The page parameters
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Page.class)
    private Page page = new Page();

    private List<Sort> sort = new ArrayList<>();

    /**
     * Grouped results based on shared fields. The most relevant document will have a _group key.
     * The key includes all other documents that share an identical value within the grouped field.
     * Documents in the _group key will not appear anywhere else in the search response.
     * @param group The group parameters
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Group.class)
    private Group group = new Group();

    /**
     * Value and Range facets for the query
     * @param facets The facets to apply to the search
     */

    private Map<String, List<Facet>> facets = new HashMap<>();


    private Map<String,List<?>> filters = new HashMap<>();
    private Integer precision;
    private List<Boost> boosts = new ArrayList<>();

    private List<String> searchFields = new ArrayList<>();
    private List<String> resultFields = new ArrayList<>();
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Analytics.class)
    private Analytics analytics = new Analytics();

    /**
     * If true, generates an analytics query event for the search request. Defaults to true.
     */
    private Boolean recordAnalytics;


    @JsonIgnore
    public SearchApiRequest addValueFilter(@NonNull String filterKey, @NonNull String... values ){
        filters.put(filterKey, Arrays.asList(values));
        return this;
    }

    @JsonIgnore
    public SearchApiRequest addValueFilter(@NonNull String filterKey, @NonNull BigDecimal... values){
        filters.put(filterKey,Arrays.asList(values));
        return this;
    }








}
