package co.elasticsearch.enterprisesearch.client.model.request.search.range;

import co.elasticsearch.enterprisesearch.client.model.request.search.filter.DateValueFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

import static co.elasticsearch.enterprisesearch.client.model.request.search.filter.DateValueFilter.DATE_PATTERN;

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

    /**
     * Inclusive lower bound of the range. Is required if to is not given.
     * @param from the lower bound
     * @return the lower bound
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private OffsetDateTime from;

    /**
     * Exclusive upper bound of the range. Is required if from is not given.
     * @param to the upper bound
     * @return the upper bound
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private OffsetDateTime to;
    /**
     * Name given to the range
     *
     * @param name Name given to the range
     * @return the name of the range
     */
    private String name;
}
