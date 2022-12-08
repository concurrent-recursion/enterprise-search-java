package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultFieldRendered {
    @Min(20)
    private Integer size;

    /**
     * If true, return the raw text field if no snippet is found. If false, only use snippets.
     *
     * @param fallback true to return the raw text field if no snippet is found. otherwise, only use snippets.
     */
    private Boolean fallback;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultFieldRendered)) return false;
        ResultFieldRendered that = (ResultFieldRendered) o;
        return Objects.equals(size, that.size) && Objects.equals(fallback, that.fallback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, fallback);
    }
}
