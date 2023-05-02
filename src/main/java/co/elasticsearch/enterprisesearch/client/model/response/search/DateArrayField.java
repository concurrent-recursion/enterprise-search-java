package co.elasticsearch.enterprisesearch.client.model.response.search;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a schema field that can contain an array of dates
 */
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
