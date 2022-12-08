package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.client.model.Sort;
import co.elasticsearch.enterprisesearch.client.model.request.boost.Boost;
import co.elasticsearch.enterprisesearch.client.model.request.facet.Facet;
import co.elasticsearch.enterprisesearch.client.model.request.filter.Filter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
     *
     * @param query String or number to match
     */
    private String query;

    /**
     * Object to delimit the pagination parameters.
     *
     * @param page The page parameters
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Page.class)
    private Page page = new Page();

    /**
     * Sort your results in an order other than document score.
     * Using sort will override the default relevance scoring method.
     * A special sorting field name is available, _score, to order by relevance.
     *
     * @param sort The fields to sort the results
     */
    private List<Sort> sort = new ArrayList<>();

    /**
     * Grouped results based on shared fields. The most relevant document will have a _group key.
     * The key includes all other documents that share an identical value within the grouped field.
     * Documents in the _group key will not appear anywhere else in the search response.
     *
     * @param group The group parameters
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Group.class)
    private Group group = new Group();

    /**
     * Value and Range facets for the query
     *
     * @param facets The facets to build on the search results
     */
    private Map<String, List<Facet>> facets = new LinkedHashMap<>();

    /**
     * Apply conditions to field values to filter results.
     *
     * @param filters The filters to apply to the search
     */
    private Filter filters;

    /**
     * Use the precision parameter of the search API to tune precision and recall for a query. Learn more in Precision tuning (beta).<br>
     * The range of values represents a sliding scale that manages the inherent tradeoff between precision and recall.
     *
     * @param precision A value between 1 and 11, inclusive. Lower values favor recall, while higher values favor precision.
     */
    @Min(1)
    @Max(11)
    private Integer precision;

    /**
     * Boosts affect the score of the entire document.<br>
     * Provide a field to boost, then increase document relevance based on values.
     *
     * @param boosts The boosts to apply to the search
     */
    @JsonProperty("boosts")
    @Getter(AccessLevel.PACKAGE)
    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    private Map<String, List<Boost>> boostMap = new LinkedHashMap<>();

    /**
     * Restricts a query to search only specific fields. Restricting fields will result in faster queries, especially for schemas with many text fields.
     */
    @JsonProperty("search_fields")
    private Map<String,SearchField> searchFields = new LinkedHashMap<>();

    /**
     * Change the fields which appear in search results and how their values are rendered.
     *
     * @param resultFields The fields to render in the results
     */
    @JsonProperty("result_fields")
    private Map<String,ResultField> resultFields = new LinkedHashMap<>();

    /**
     * Submit tags with the analytics parameter. Tags can be used to enrich each query with unique information.
     * Once added, a tag cannot be removed.
     *
     * @param analytics The analytics tags for this query
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Analytics.class)
    private Analytics analytics = new Analytics();

    /**
     * If true, generates an analytics query event for the search request. Defaults to true.
     *
     * @param recordAnalytics Whether analytics should be recorded
     */
    @JsonProperty("record_analytics")
    private Boolean recordAnalytics;

    public SearchApiRequest withSorts(Sort... sorts){
        this.sort = Arrays.asList(sorts);
        return this;
    }

    public SearchApiRequest withTags(String... tags){
        analytics.withTags(tags);
        return this;
    }

    public SearchApiRequest addFacets(String name, Facet... facets){
        this.facets.put(name,Arrays.asList(facets));
        return this;
    }

    public SearchApiRequest withBoosts(Boost... boosts){
        for(Boost boost : boosts){
            this.boostMap.put(boost.getName(),Collections.singletonList(boost));
        }
        return this;
    }

    @JsonIgnore
    public List<Boost> getBoosts(){
        return this.boostMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    void setBoostMap(Map<String,List<Boost>> boosts){
        for(Map.Entry<String,List<Boost>> field : boosts.entrySet()){
            for(Boost b : field.getValue()){
                b.setName(field.getKey());
            }
        }
        this.boostMap = boosts;
    }

}
