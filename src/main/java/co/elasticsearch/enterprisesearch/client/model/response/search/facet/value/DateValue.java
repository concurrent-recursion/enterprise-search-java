package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class DateValue implements FacetValueValue<OffsetDateTime> {
    private OffsetDateTime value;
    private Long count;
}
