package co.elasticsearch.enterprisesearch.client.model.request.filter;

import co.elasticsearch.enterprisesearch.client.model.request.range.DateRange;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.Map;

import static co.elasticsearch.enterprisesearch.client.model.request.filter.DateValueFilter.DATE_PATTERN;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateRangeFilter implements Filter, Map.Entry<String, DateRange>{
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private OffsetDateTime from;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private OffsetDateTime to;

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public DateRange getValue() {
        return new DateRange().setFrom(from).setTo(to);
    }

    @Override
    public DateRange setValue(DateRange value) {
        this.from = value.getFrom();
        this.to = value.getTo();
        return value;
    }

    public DateRangeFilter setFilterValue(DateRange range){
        setValue(range);
        return this;
    }
}
