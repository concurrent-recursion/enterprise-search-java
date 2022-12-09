package co.elasticsearch.enterprisesearch.client.model.request.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RequiredArgsConstructor
@ToString
public class NumberValueFilter implements Filter {
    /**
     * The field from your schema upon which to apply your filter
     * @param name the field name
     * @return the field name
     */
    private final String name;

    /**
     * The value upon which to filter. The value must be an exact match
     * @param values The values to match on
     * @return The values
     */
    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    private List<BigDecimal> values = new ArrayList<>();

    public NumberValueFilter(String name, BigDecimal... values) {
        this(name);
        this.values.addAll(Arrays.asList(values));
    }

    public NumberValueFilter(String name, Integer... values) {
        this(name);
        this.values.addAll(Arrays.stream(values).map(BigDecimal::new).collect(Collectors.toList()));
    }

    public NumberValueFilter(String name, Long... values) {
        this(name);
        this.values.addAll(Arrays.stream(values).map(BigDecimal::new).collect(Collectors.toList()));
    }

    public NumberValueFilter(String name, Float... values) {
        this(name);
        this.values.addAll(Arrays.stream(values).map(BigDecimal::valueOf).collect(Collectors.toList()));
    }

    public NumberValueFilter(String name, Double... values) {
        this(name);
        this.values.addAll(Arrays.stream(values).map(BigDecimal::valueOf).collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberValueFilter)) return false;
        NumberValueFilter that = (NumberValueFilter) o;
        return name.equals(that.name) && values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, values);
    }
}
