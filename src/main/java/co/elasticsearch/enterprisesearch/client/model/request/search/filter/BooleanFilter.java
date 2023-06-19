package co.elasticsearch.enterprisesearch.client.model.request.search.filter;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * Represents a Boolean Filter, which can contain child filters
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class BooleanFilter implements Filter {
    /**
     * All the filters must match. This functions as an <em>AND</em> condition.
     * @param all The filers to match on
     * @return The filters
     */
    private List<Filter> all = new ArrayList<>();
    /**
     * At least one of the filters must match. This functions as an <em>OR</em> condition.
     * @param any The filters to match on
     * @return the filters
     */
    private List<Filter> any = new ArrayList<>();
    /**
     * All the filters must <em>not</em> match. This functions as a <em>NOT</em> condition.
     * @param none the filters to match on
     * @return the filters
     */
    private List<Filter> none = new ArrayList<>();

    /**
     * Set the list of all filters with the given filters
     * @param filters the filters to match on
     * @return This filters
     */
    public BooleanFilter allOf(Filter... filters) {
        all.addAll(Arrays.asList(filters));
        return this;
    }

    /**
     * Set the list of any filters with the given filters
     * @param filters the filters to match on
     * @return This filter
     */
    public BooleanFilter anyOf(Filter... filters) {
        any.addAll(Arrays.asList(filters));
        return this;
    }

    /**
     * Set the list of none filters with the given filters
     * @param filters the filters to match on
     * @return This filter
     */
    public BooleanFilter noneOf(Filter... filters) {
        none.addAll(Arrays.asList(filters));
        return this;
    }


    @JsonValue
    Map<String, List<Filter>> values() {
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
