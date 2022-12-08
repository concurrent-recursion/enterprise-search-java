package co.elasticsearch.enterprisesearch.client.model.request.filter;

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
    @JsonIgnore
    private final String name;

    @JsonIgnore
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
