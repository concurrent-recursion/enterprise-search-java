package co.elasticsearch.enterprisesearch.client.model.response.searchsettings;

import co.elasticsearch.enterprisesearch.client.model.request.search.ResultField;
import co.elasticsearch.enterprisesearch.client.model.request.search.SearchField;
import co.elasticsearch.enterprisesearch.client.model.request.search.boost.Boost;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
@JsonPropertyOrder({"search_fields","result_fields","boosts","precision","precision_enabled"})
public class SearchSettings {
    @Getter(AccessLevel.PACKAGE)
    @JsonProperty("search_fields")
    private Map<String, SearchField> searchFieldMap = new LinkedHashMap<>();
    @Getter(AccessLevel.PACKAGE)
    @JsonProperty("result_fields")
    private Map<String, ResultField> resultFieldMap = new HashMap<>();
    @JsonProperty("boosts")
    @Getter(AccessLevel.PACKAGE)
    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    private Map<String, List<Boost>> boostMap = new LinkedHashMap<>();

    /**
     * The value of the precision parameter must be an integer between 1 and 11, inclusive. The range of values represents a sliding scale that manages the inherent tradeoff between precision and recall. Lower values favor recall, while higher values favor precision.
     * @param precision the precision
     * @return the precision
     */
    private Integer precision;
    /**
     * Whether precision is enabled on this engine
     * @param precisionEnabled whether precision is enabled
     * @return true if precision is enabled, otherwise false
     */
    @JsonProperty("precision_enabled")
    private Boolean precisionEnabled;

    /**
     * Get the result fields
     * @return the result fields
     */
    @JsonIgnore
    public Collection<ResultField> getResults() {
        return this.resultFieldMap.values();
    }

    /**
     * Add a result field to the settings
     * @param resultField the result field
     * @return this search settings
     */
    @JsonIgnore
    public SearchSettings addResultField(ResultField resultField){
        this.resultFieldMap.put(resultField.getName(),resultField);
        return this;
    }

    void setResultFieldMap(Map<String,ResultField> resultFieldMap){
        for(Map.Entry<String,ResultField> entry : resultFieldMap.entrySet()){
            entry.getValue().setName(entry.getKey());
        }
        this.resultFieldMap = resultFieldMap;
    }


    /**
     * Adds a search field to the settings
     * @param searchField the search field
     * @return this search settings
     */
    @JsonIgnore
    public SearchSettings addSearchField(SearchField searchField){
        this.searchFieldMap.put(searchField.getName(),searchField);
        return this;
    }

    /**
     * Get the search fields
     * @return the search fields
     */
    @JsonIgnore
    public Collection<SearchField> getSearchFields() {
        return this.searchFieldMap.values();
    }

    void setSearchFieldMap(Map<String,SearchField> searchFieldMap){
        for(Map.Entry<String,SearchField> searchField : searchFieldMap.entrySet()){
            searchField.getValue().setName(searchField.getKey());
        }
        this.searchFieldMap = searchFieldMap;
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
     * Add Boost to the search settings
     * @param boost the boost
     * @return this search settings
     */
    public SearchSettings addBoost(Boost boost){
        this.boostMap.putIfAbsent(boost.getName(),new ArrayList<>());
        this.boostMap.get(boost.getName()).add(boost);
        return this;
    }

    void setBoostMap(Map<String,List<Boost>> boostMap){
        for(Map.Entry<String,List<Boost>> boost : boostMap.entrySet()){
            for(Boost b : boost.getValue()){
                b.setName(boost.getKey());
            }
        }
        this.boostMap = boostMap;
    }
}
