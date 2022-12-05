package co.elasticsearch.enterprisesearch.client.model.request.range;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static co.elasticsearch.enterprisesearch.client.model.request.filter.DateValueFilter.DATE_PATTERN;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
public class DateRange implements Range {

    public DateRange(){}
    public DateRange(OffsetDateTime from, OffsetDateTime to){
        this.from = from;
        this.to = to;
    }
    public DateRange(String from, String to){
        if(from != null){
            this.from = OffsetDateTime.parse(from, DateTimeFormatter.ofPattern(DATE_PATTERN));
        }
        if(to != null){
            this.to = OffsetDateTime.parse(to,DateTimeFormatter.ofPattern(DATE_PATTERN));
        }
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private OffsetDateTime from;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private OffsetDateTime to;
    /**
     * Name given to the range
     *
     * @param name Name given to the range
     */
    private String name;
}
