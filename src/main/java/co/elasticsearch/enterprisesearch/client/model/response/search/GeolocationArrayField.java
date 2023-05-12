package co.elasticsearch.enterprisesearch.client.model.response.search;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a field with 1 or more Geolocation values
 */
@Getter
@Setter
@Accessors(chain = true)
public class GeolocationArrayField {

    /**
     * The raw Geolocation array value
     *
     * @param raw the value
     * @return the value
     */
    private List<BigDecimal[]> raw = new ArrayList<>();
}
