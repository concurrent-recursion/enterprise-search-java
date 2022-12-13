package co.elasticsearch.enterprisesearch.client.model.response.schemas;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Schema {

    @JsonAnyGetter
    @JsonAnySetter
    private final Map<String, FieldType> fields = new LinkedHashMap<>();

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
}
