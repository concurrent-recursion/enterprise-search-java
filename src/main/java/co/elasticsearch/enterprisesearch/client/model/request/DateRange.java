package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class DateRange implements Range {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SS]XXX")
    private OffsetDateTime from;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SS]XXX")
    private OffsetDateTime to;
    /**
     * Name given to the range
     *
     * @param name Name given to the range
     */
    private String name;
}
