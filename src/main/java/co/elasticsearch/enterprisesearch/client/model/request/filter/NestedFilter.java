package co.elasticsearch.enterprisesearch.client.model.request.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NestedFilter implements ComposableFilter,Filter {
    private List<Map<String, Filter>> all = new ArrayList<>();
    private List<Map<String, Filter>> any = new ArrayList<>();
    private List<Map<String, Filter>> none = new ArrayList<>();
}
