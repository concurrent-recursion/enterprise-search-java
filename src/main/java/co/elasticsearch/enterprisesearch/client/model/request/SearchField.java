package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchField {

    public SearchField() {
    }

    public SearchField(String fieldName) {
        this.name = fieldName;
    }

    /**
     * The field name
     * @param name the field name
     * @return the field name
     */
    @JsonIgnore
    private String name;
    /**
     * Weight is given between 10 (most relevant) to 1 (least relevant)
     * @param weight A value between 1 and 10 for relevance
     * @return the weight
     */
    @Min(1)
    @Max(10)
    private Integer weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchField)) return false;
        SearchField that = (SearchField) o;
        return name.equals(that.name) && Objects.equals(weight, that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight);
    }
}
