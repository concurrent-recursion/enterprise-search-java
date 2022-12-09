package co.elasticsearch.enterprisesearch.client.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DateArrayField implements Field {
    /**
     * The raw value of dates
     * @param raw the raw value
     * @return the raw values
     */
    private List<OffsetDateTime> raw = new ArrayList<>();
}
