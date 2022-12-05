package co.elasticsearch.enterprisesearch.client.model.request.filter;

import co.elasticsearch.enterprisesearch.client.model.request.range.NumberRange;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NumberRangeFilter implements Filter, Map.Entry<String, NumberRange> {
    private String name;
    private BigDecimal from;
    private BigDecimal to;

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public NumberRange getValue() {
        return new NumberRange().setFrom(from).setTo(to);
    }

    @Override
    public NumberRange setValue(NumberRange value) {
        this.from = value.getFrom();
        this.to = value.getTo();
        return value;
    }

    public NumberRangeFilter setRange(NumberRange range){
        setValue(range);
        return this;
    }
}
