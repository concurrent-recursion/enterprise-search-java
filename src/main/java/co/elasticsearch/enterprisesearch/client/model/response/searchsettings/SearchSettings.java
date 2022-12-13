package co.elasticsearch.enterprisesearch.client.model.response.searchsettings;

import co.elasticsearch.enterprisesearch.client.model.request.ResultField;
import co.elasticsearch.enterprisesearch.client.model.request.SearchField;
import co.elasticsearch.enterprisesearch.client.model.request.boost.Boost;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class SearchSettings {
    @Getter(AccessLevel.PACKAGE)
    @JsonProperty("search_fields")
    private Map<String, SearchField> searchFieldMap = new HashMap<>();
    @Getter(AccessLevel.PACKAGE)
    @JsonProperty("result_fields")
    private Map<String, ResultField> resultFieldMap = new HashMap<>();
    @JsonProperty("boosts")
    @Getter(AccessLevel.PACKAGE)
    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    private Map<String, List<Boost>> boostMap = new LinkedHashMap<>();

    private Integer precision;
    @JsonProperty("precision_enabled")
    private Boolean precisionEnabled;

    /**
     * Get the result fields
     * @return the result fields
     */
    @JsonIgnore
    public List<ResultField> getResults() {
        return new ArrayList<>(this.resultFieldMap.values());
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
     * Get the boosts
     * @return the boosts
     */
    @JsonIgnore
    public List<Boost> getBoosts() {
        return this.boostMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }
}
