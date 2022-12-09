package co.elasticsearch.enterprisesearch.client.model.request.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RequiredArgsConstructor
@ToString
public class TextValueFilter implements Filter {
    /**
     * The field from your schema upon which to apply your filter
     * @param name the field name
     * @return the field name
     */
    private final String name;

    /**
     * The value upon which to filter. The value must be an exact match, even casing: True will not match on true.
     * @param values The values to match on
     * @return The values
     */
    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    private List<String> values = new ArrayList<>();


    public TextValueFilter(String name, String... values) {
        this(name, Arrays.asList(values));
    }

    public TextValueFilter(String name, List<String> values) {
        this(name);
        this.values.addAll(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextValueFilter)) return false;
        TextValueFilter that = (TextValueFilter) o;
        return name.equals(that.name) && values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, values);
    }
}
