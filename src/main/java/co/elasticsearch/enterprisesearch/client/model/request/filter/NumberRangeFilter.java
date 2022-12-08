package co.elasticsearch.enterprisesearch.client.model.request.filter;

import co.elasticsearch.enterprisesearch.client.model.request.range.NumberRange;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@ToString
public class NumberRangeFilter implements Filter {
    private final String name;
    private BigDecimal from;
    private BigDecimal to;

    public NumberRangeFilter(String name, BigDecimal from, BigDecimal to){
        this(name);
        this.from = from;
        this.to = to;
    }
    public NumberRangeFilter(String name, Integer from, Integer to){
        this(name);
        if(from != null) {
            this.from = new BigDecimal(from);
        }
        if(to != null) {
            this.to = new BigDecimal(to);
        }
    }
    public NumberRangeFilter(String name, Long from, Long to){
        this(name);
        if(from != null) {
            this.from = new BigDecimal(from);
        }
        if(to != null) {
            this.to = new BigDecimal(to);
        }
    }
    public NumberRangeFilter(String name, Float from, Float to){
        this(name);
        if(from != null) {
            this.from = BigDecimal.valueOf(from);
        }
        if(to != null) {
            this.to = BigDecimal.valueOf(to);
        }
    }
    public NumberRangeFilter(String name, Double from, Double to){
        this(name);
        if(from != null) {
            this.from = BigDecimal.valueOf(from);
        }
        if(to != null) {
            this.to = BigDecimal.valueOf(to);
        }
    }


    public NumberRangeFilter setRange(NumberRange range){
        this.from = range == null ? null : range.getFrom();
        this.to = range == null ? null : range.getTo();
        return this;
    }
    public NumberRange getRange(){
        return from == null && to == null ? null : new NumberRange(from,to);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberRangeFilter)) return false;
        NumberRangeFilter that = (NumberRangeFilter) o;
        return name.equals(that.name) && Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, from, to);
    }
}
