package co.elasticsearch.enterprisesearch.client.model.request.search;

import co.elasticsearch.enterprisesearch.client.model.Sort;
import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.request.search.boost.Boost;
import co.elasticsearch.enterprisesearch.client.model.request.search.facet.Facet;
import co.elasticsearch.enterprisesearch.client.model.request.search.filter.Filter;
import co.elasticsearch.enterprisesearch.client.model.request.search.filter.Filters;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a request to the search api
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchRequest {

    /**
     * String or number to match.<br>
     * The value "" (empty string) matches all documents. (Note that API limits for documents returned applies.)<br>
     * The following Lucene query syntax is supported:
     * <ul>
     *     <li>double quoted strings</li>
     *     <li><code>+</code> and <code>-</code></li>
     *     <li><code>AND</code>, <code>OR</code>, <code>NOT</code></li>
     * </ul>
     *
     * @param query String or number to match
     * @return query to match
     */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String query = "";

    /**
     * Object to delimit the pagination parameters.
     * @param page The page parameters
     * @return the page
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Page.class)
    private Page page = new Page();

    /**
     * Sort your results in an order other than document score.
     * Using sort will override the default relevance scoring method.
     * A special sorting field name is available, _score, to order by relevance.
     * @param sort The fields to sort the results
     * @return the sorts
     */
    private List<Sort> sort = new ArrayList<>();

    /**
     * Grouped results based on shared fields. The most relevant document will have a _group key.
     * The key includes all other documents that share an identical value within the grouped field.
     * Documents in the _group key will not appear anywhere else in the search response.
     * @param group The group parameters
     * @return the groups
     */
    private Group group = null;

    /**
     * Value and Range facets for the query
     * @param facetMap The facets to build on the search results
     * @return the facetMap of facets
     */
    @JsonProperty("facets")
    @Getter(AccessLevel.PACKAGE)

    private Map<String, List<Facet>> facetMap = new LinkedHashMap<>();

    /**
     * Apply conditions to field values to filter results.
     * @param filters The filters to apply to the search
     * @return the filters
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Filter internalFilter;


    @JsonSetter("filters")
    void setFilter(Filter filter){
        setFilters(filter);
    }

    @JsonGetter("filters")
    Filter getFilter(){
        return internalFilter;
    }


    @JsonIgnore
    private Filters filters;

    /**
     * Get the search filter from the search
     * @return The Filters object
     */
    @JsonIgnore
    public Filters getFilters(){
        return filters;
    }

    /**
     * Set the filter for this search request
     * @param filter The filter to use for the search
     * @return The search request
     */
    @JsonIgnore
    public SearchRequest setFilters(Filter filter){
        this.internalFilter = filter;
        this.filters = new Filters(filter);
        return this;
    }

    /**
     * Use the precision parameter of the search API to tune precision and recall for a query. Learn more in Precision tuning (beta).<br>
     * The range of values represents a sliding scale that manages the inherent tradeoff between precision and recall.
     * @param precision A value between 1 and 11, inclusive. Lower values favor recall, while higher values favor precision.
     * @return the precision
     */
    @Min(1)
    @Max(11)
    private Integer precision;

    /**
     * Boosts affect the score of the entire document.<br>
     * Provide a field to boost, then increase document relevance based on values.
     * @param boostMap The boosts to apply to the search
     * @return the boosts
     */
    @JsonProperty("boosts")
    @Getter(AccessLevel.PACKAGE)
    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    private Map<String, List<Boost>> boostMap = new LinkedHashMap<>();


    /**
     * Restricts a query to search only specific fields. Restricting fields will result in faster queries, especially for schemas with many text fields.
     * Only available within text fields.
     * @param searchFieldMap the search fields
     * @return the search fields
     */
    @JsonProperty("search_fields")
    @Getter(AccessLevel.PACKAGE)
    private Map<String, SearchField> searchFieldMap = new LinkedHashMap<>();

    /**
     * Change the fields which appear in search results and how their values are rendered.
     * @param resultFieldMap The fields to render in the results
     * @return the result fields
     */
    @JsonProperty("result_fields")
    @Getter(AccessLevel.PACKAGE)
    private Map<String, ResultField> resultFieldMap = new LinkedHashMap<>();

    /**
     * Submit tags with the analytics parameter. Tags can be used to enrich each query with unique information.
     * Once added, a tag cannot be removed.
     *
     * @param analytics The analytics tags for this query
     * @return the analytics
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Analytics.class)
    private Analytics analytics = new Analytics();

    /**
     * If true, generates an analytics query event for the search request. Defaults to true.
     *
     * @param recordAnalytics Whether analytics should be recorded
     * @return true if analytics are recorded
     */
    @JsonProperty("record_analytics")
    private Boolean recordAnalytics;

    /**
     * Sort your results in an order other than document score.
     * Using sort will override the default relevance scoring method.
     * A special sorting field name is available, _score, to order by relevance.
     * @param sorts The fields to sort the results
     * @return This request
     */
    public SearchRequest withSorts(Sort... sorts) {
        this.sort = Arrays.asList(sorts);
        return this;
    }
    /**
     * Tags can be used to enrich each query with unique information.
     * @param tags The analytics tags for this query
     * @return this request
     */
    public SearchRequest withTags(String... tags) {
        analytics.withTags(tags);
        return this;
    }

    /**
     * Provides the counts of each value or range for a field. When faceting on an array field, each unique value will be
     * included in the response. Each value is only counted once.
     * @param facets the facets
     * @return this request
     */
    public SearchRequest withFacets(Facet... facets) {
        for (Facet facet : facets) {
            this.facetMap.putIfAbsent(facet.getFieldName(), new ArrayList<>());
            this.facetMap.get(facet.getFieldName()).add(facet);
        }
        return this;
    }

    /**
     * Provide a field to boost, then increase document relevance based on values.
     * Boosts affect the score of the entire document.
     * @param boosts The boosts to apply
     * @return this request
     */
    public SearchRequest withBoosts(Boost... boosts) {
        for (Boost boost : boosts) {
            this.boostMap.put(boost.getName(), Collections.singletonList(boost));
        }
        return this;
    }

    /**
     * Restricts a query to search only specific fields. Restricting fields will result in faster queries, especially
     * for schemas with many text fields. Only available within text fields.
     * @param fields The fields
     * @return this request
     */
    public SearchRequest withSearchFields(SearchField... fields) {
        for (SearchField field : fields) {
            this.searchFieldMap.put(field.getName(), field);
        }
        return this;
    }

    /**
     * Change the fields which appear in search results and how their values are rendered.
     * @param fields The fields to include in the result
     * @return The fields to include in the result
     */
    public SearchRequest withResultFields(ResultField... fields) {
        for (ResultField field : fields) {
            this.resultFieldMap.put(field.getName(), field);
        }
        return this;
    }

    /**
     * Get the boosts
     * @return the boosts
     */
    @JsonIgnore
    public List<Boost> getBoosts() {
        return this.boostMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * Get the search fields
     * @return the search fields
     */
    @JsonIgnore
    public List<SearchField> getSearchFields() {
        return new ArrayList<>(this.searchFieldMap.values());
    }

    /**
     * Get the result fields
     * @return the result fields
     */
    @JsonIgnore
    public List<ResultField> getResultFields() {
        return new ArrayList<>(this.resultFieldMap.values());
    }

    /**
     * Get the facets
     * @return The facets
     */
    @JsonIgnore
    public List<Facet> getFacets() {
        return facetMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    void setBoostMap(Map<String, List<Boost>> boosts) {
        for (Map.Entry<String, List<Boost>> field : boosts.entrySet()) {
            for (Boost b : field.getValue()) {
                b.setName(field.getKey());
            }
        }
        this.boostMap = boosts;
    }

    void setSearchFieldMap(Map<String, SearchField> searchFieldMap) {
        for (Map.Entry<String, SearchField> field : searchFieldMap.entrySet()) {
            field.getValue().setName(field.getKey());
        }
        this.searchFieldMap = searchFieldMap;
    }

    void setResultFieldMap(Map<String, ResultField> resultFieldMap) {
        for (Map.Entry<String, ResultField> field : resultFieldMap.entrySet()) {
            field.getValue().setName(field.getKey());
        }
        this.resultFieldMap = resultFieldMap;
    }

    void setFacetMap(Map<String, List<Facet>> facetMap) {
        for (Map.Entry<String, List<Facet>> facet : facetMap.entrySet()) {
            facet.getValue().forEach(f -> f.setFieldName(facet.getKey()));
        }
        this.facetMap = facetMap;
    }

}
