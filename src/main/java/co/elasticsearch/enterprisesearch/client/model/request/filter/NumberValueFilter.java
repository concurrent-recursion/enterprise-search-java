package co.elasticsearch.enterprisesearch.client.model.request.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RequiredArgsConstructor
public class NumberValueFilter implements Filter, Map.Entry<String,List<BigDecimal>> {
    private final String name;

    @Override
    public String getKey() {
        return name;
    }

    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    @Override
    public List<BigDecimal> getValue() {
        return values;
    }

    @Override
    public List<BigDecimal> setValue(List<BigDecimal> value) {
        this.values = value;
        return value;
    }

    @JsonIgnore
    private List<BigDecimal> values = new ArrayList<>();
}
