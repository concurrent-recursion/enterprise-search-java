package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class DateRange implements FacetRangeValue<OffsetDateTime> {
    private OffsetDateTime from;
    private OffsetDateTime to;
    private Long count;
    private String name;
}
