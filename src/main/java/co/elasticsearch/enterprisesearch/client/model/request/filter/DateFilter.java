package co.elasticsearch.enterprisesearch.client.model.request.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DateFilter implements Filter {
    private List<OffsetDateTime> values = new ArrayList<>();
}
