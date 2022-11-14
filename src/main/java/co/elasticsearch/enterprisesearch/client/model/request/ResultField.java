package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResultField {
    /**
     * An exact representation of the value within a field. And it is exact! It is not HTML escaped.
     * @param raw The raw field
     */
    private ResultFieldRendered raw;
    /**
     * A snippet is an HTML escaped representation of the value within a field, where query matches are captured in <tt>&lt;em&gt;</tt> tags.
     * @param snippet The snippet field
     */
    private ResultFieldRendered snippet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultField)) return false;
        ResultField that = (ResultField) o;
        return Objects.equals(raw, that.raw) && Objects.equals(snippet, that.snippet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(raw, snippet);
    }
}
