package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

import java.util.*;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchApiRequest {

    private String query;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Page.class)
    private Page page = new Page();
    private List<Sort> sort = new ArrayList<>();
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Group.class)
    private Group group = new Group();
    private Map<String, ArrayNode> facets = new HashMap<>();
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Analytics.class)
    private Analytics analytics = new Analytics();

    private Map<String,List<?>> filters = new HashMap<>();

    @JsonIgnore
    public SearchApiRequest addFacet(@NonNull String fieldKey,@NonNull SearchFacet facet){
        facets.put(fieldKey, JsonNodeFactory.instance.arrayNode().addPOJO(facet));
        return this;
    }

    @JsonIgnore
    public SearchApiRequest addFacet(@NonNull String fieldKey,@NonNull SearchRangeFacet facet){
        facets.put(fieldKey, JsonNodeFactory.instance.arrayNode().addPOJO(facet));
        return this;
    }

    @JsonIgnore
    public SearchApiRequest addFilter(@NonNull String filterKey, @NonNull String... values ){
        filters.put(filterKey, Arrays.asList(values));
        return this;
    }

    @JsonIgnore
    public SearchApiRequest addFilter(@NonNull String filterKey, @NonNull Long... values){
        filters.put(filterKey,Arrays.asList(values));
        return this;
    }






}
