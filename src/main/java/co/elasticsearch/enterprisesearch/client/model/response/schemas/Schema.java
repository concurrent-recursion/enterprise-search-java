package co.elasticsearch.enterprisesearch.client.model.response.schemas;

import co.elasticsearch.enterprisesearch.client.model.response.ErrorableResponse;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
public class Schema implements ErrorableResponse {

    /**
     * The 
     */
    @JsonAnyGetter
    @JsonAnySetter
    private final Map<String, FieldType> fields = new LinkedHashMap<>();

    /**
     * Get the errors
     * @param errors the errors
     * @return the errors
     */
    @Getter
    @Setter
    private List<String> errors = new ArrayList<>();

    @JsonIgnore
    public FieldType getField(String fieldName){
        return fields.get(fieldName);
    }

    @JsonIgnore
    public Schema setField(String fieldName, FieldType fieldType){
        fields.put(fieldName, fieldType);
        return this;
    }

    @JsonIgnore
    public Set<String> getFieldNames(){
        return fields.keySet();
    }

    @JsonIgnore
    public boolean isError(){
        return !errors.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schema)) return false;
        Schema schema = (Schema) o;
        return Objects.equals(fields, schema.fields) && Objects.equals(errors, schema.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields, errors);
    }
}
