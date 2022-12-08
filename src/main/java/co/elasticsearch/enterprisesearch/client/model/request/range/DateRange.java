package co.elasticsearch.enterprisesearch.client.model.request.range;

import co.elasticsearch.enterprisesearch.client.model.request.filter.DateValueFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

import static co.elasticsearch.enterprisesearch.client.model.request.filter.DateValueFilter.DATE_PATTERN;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
public class DateRange implements Range<OffsetDateTime> {

    public DateRange() {
    }

    public DateRange(OffsetDateTime from, OffsetDateTime to) {
        this.from = from;
        this.to = to;
    }

    public DateRange(String from, String to) {
        if (from != null) {
            this.from = OffsetDateTime.parse(from, DateValueFilter.RFC_3339);
        }
        if (to != null) {
            this.to = OffsetDateTime.parse(to, DateValueFilter.RFC_3339);
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
