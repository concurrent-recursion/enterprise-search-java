package co.elasticsearch.enterprisesearch.client.model.response.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

import static co.elasticsearch.enterprisesearch.client.model.request.search.filter.DateValueFilter.DATE_PATTERN;

/**
 * Represents a date field in the engine schema
 */
@Getter
@Setter
@Accessors(chain = true)
public class DateField implements Field {
    /**
     * The raw date value
     * @param raw the value
     * @return the value
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private OffsetDateTime raw;
}
