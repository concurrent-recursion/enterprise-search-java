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

/**
 * Represents an engine's schema
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
public class Schema implements ErrorableResponse {

    /**
     * The field in the schema
     * @param fields the fields in the schema
     * @return the fieldws in the schema
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

    /**
     * Get the field type for the given field name
     * @param fieldName The name of the field
     * @return the field type for this given field, or null if the field does not exist
     */
    @JsonIgnore
    public FieldType getField(String fieldName){
        return fields.get(fieldName);
    }

    /**
     * Set the field type for the given field
     * @param fieldName The name of the field
     * @param fieldType The type of the field
     * @return the updated schema object
     */
    @JsonIgnore
    public Schema setField(String fieldName, FieldType fieldType){
        fields.put(fieldName, fieldType);
        return this;
    }

    /**
     * Get a set of all the field names in this schema
     * @return a set of all the field names in this schema
     */
    @JsonIgnore
    public Set<String> getFieldNames(){
        return fields.keySet();
    }

    @JsonIgnore
    @Override
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
