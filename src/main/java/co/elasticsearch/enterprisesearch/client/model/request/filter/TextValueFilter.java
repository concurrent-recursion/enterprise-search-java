package co.elasticsearch.enterprisesearch.client.model.request.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TextValueFilter implements Filter, Map.Entry<String,List<String>> {
    @JsonIgnore
    private String name;

    @JsonIgnore
    private List<String> values = new ArrayList<>();

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public List<String> getValue() {
        return values;
    }

    @Override
    public List<String> setValue(List<String> value) {
        this.values = value;
        return value;
    }
}
