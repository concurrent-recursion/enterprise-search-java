package co.elasticsearch.enterprisesearch.client.model.request.filter;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class BooleanFilter implements Filter {
    private List<Filter> all = new ArrayList<>();
    private List<Filter> any = new ArrayList<>();
    private List<Filter> none = new ArrayList<>();

    public BooleanFilter allOf(Filter... filters) {
        all.addAll(Arrays.asList(filters));
        return this;
    }

    public BooleanFilter anyOf(Filter... filters) {
        any.addAll(Arrays.asList(filters));
        return this;
    }

    public BooleanFilter noneOf(Filter... filters) {
        none.addAll(Arrays.asList(filters));
        return this;
    }


    @JsonValue
    public Map<String, List<Filter>> values() {
        Map<String, List<Filter>> values = new LinkedHashMap<>();
        if (!all.isEmpty()) {
            values.put("all", all);
        }
        if (!any.isEmpty()) {
            values.put("any", any);
        }
        if (!none.isEmpty()) {
            values.put("none", none);
        }
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanFilter)) return false;
        BooleanFilter that = (BooleanFilter) o;
        return Objects.equals(all, that.all) && Objects.equals(any, that.any) && Objects.equals(none, that.none);
    }

    @Override
    public int hashCode() {
        return Objects.hash(all, any, none);
    }
}
