package co.elasticsearch.enterprisesearch.client.model.request.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class DateRangeFilter implements Filter{
    private OffsetDateTime from;
    private OffsetDateTime to;
}
