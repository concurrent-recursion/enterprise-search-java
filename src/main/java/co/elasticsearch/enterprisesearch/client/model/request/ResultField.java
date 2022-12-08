package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@JsonSerialize(using = ResultFieldSerializer.class)
public class ResultField {

    public ResultField() {
    }

    public ResultField(String name) {
        this.name = name;
    }

    @JsonIgnore
    private String name;
    /**
     * An exact representation of the value within a field. And it is exact! It is not HTML escaped.
     *
     * @param raw The raw field
     */
    @Getter(AccessLevel.PACKAGE)
    private ResultFieldRendered raw;
    /**
     * A snippet is an HTML escaped representation of the value within a field, where query matches are captured in <tt>&lt;em&gt;</tt> tags.
     *
     * @param snippet The snippet field
     */
    @Getter(AccessLevel.PACKAGE)
    private ResultFieldRendered snippet;

    public ResultField withRaw() {
        raw = new ResultFieldRendered();
        return this;
    }

    public ResultField withRaw(int size) {
        raw = new ResultFieldRendered().setSize(size);
        return this;
    }

    public ResultField withoutRaw() {
        raw = null;
        return this;
    }

    public ResultField withSnippet() {
        snippet = new ResultFieldRendered();
        return this;
    }

    public ResultField withSnippet(int size) {
        snippet = new ResultFieldRendered().setSize(size);
        return this;
    }

    public ResultField withSnippet(int size, Boolean fallback) {
        snippet = new ResultFieldRendered().setSize(size).setFallback(fallback);
        return this;
    }

    public ResultField withSnippet(Boolean fallback) {
        snippet = new ResultFieldRendered().setFallback(fallback);
        return this;
    }

    public ResultField withoutSnippet() {
        snippet = null;
        return this;
    }


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
