package co.elasticsearch.enterprisesearch.client.model.request.search;

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

    /**
     * The name of the field
     * @param name the field name
     * @return the field name
     */
    @JsonIgnore
    private String name;
    /**
     * An exact representation of the value within a field. And it is exact! It is not HTML escaped.
     *
     * @param raw The raw field
     * @return the raw field
     */
    @Getter(AccessLevel.PACKAGE)
    private ResultFieldRendered raw;
    /**
     * A snippet is an HTML escaped representation of the value within a field, where query matches are captured in <code>&lt;em&gt;</code> tags.
     *
     * @param snippet The snippet field
     * @return the snippet field
     */
    @Getter(AccessLevel.PACKAGE)
    private ResultFieldRendered snippet;

    /**
     * Enable the return of the raw representation of this field
     * @return this field
     */
    public ResultField withRaw() {
        raw = new ResultFieldRendered();
        return this;
    }
    /**
     * Enable the return of the raw representation of this field, with the given field size max
     * @param size the maximum number of characters to return
     * @return this field
     */

    public ResultField withRaw(int size) {
        raw = new ResultFieldRendered().setSize(size);
        return this;
    }

    /**
     * Disable the return of the raw representation of this field
     * @return this field
     */
    public ResultField withoutRaw() {
        raw = null;
        return this;
    }

    /**
     * Enable the return of the snippet representation of this field
     * @return this field
     */
    public ResultField withSnippet() {
        snippet = new ResultFieldRendered();
        return this;
    }

    /**
     * Enable the return of the snippet representation of this field, with the given max length
     * @param size Character length of the snippet returned. Must be at least 20; defaults to 100.
     * @return this field
     */
    public ResultField withSnippet(int size) {
        snippet = new ResultFieldRendered().setSize(size);
        return this;
    }

    /**
     * Enable the return of the snippet representation of this field, with given max length, and fallback field config
     * @param size Character length of the snippet returned. Must be at least 20; defaults to 100.
     * @param fallback If true, return the raw text field if no snippet is found. If false, only use snippets.
     * @return this field
     */
    public ResultField withSnippet(int size, Boolean fallback) {
        snippet = new ResultFieldRendered().setSize(size).setFallback(fallback);
        return this;
    }
    /**
     * Enable the return of the snippet representation of this field, with fallback field config
     * @param fallback If true, return the raw text field if no snippet is found. If false, only use snippets.
     * @return this field
     */

    public ResultField withSnippet(Boolean fallback) {
        snippet = new ResultFieldRendered().setFallback(fallback);
        return this;
    }

    /**
     * Disable the return of the snippet representation of this field
     * @return this field
     */
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
