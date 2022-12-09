package co.elasticsearch.enterprisesearch.client.model.request.boost;

import co.elasticsearch.enterprisesearch.client.model.GeoLocation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"type", "function", "center", "factor"})
public class GeolocationProximityBoost implements Boost {
    private String name;

    public BoostType getType() {
        return BoostType.PROXIMITY;
    }

    /**
     * Type of function to calculate the boost value. Can be linear, exponential, or gaussian.
     *
     * @param function The function to calculate the boost
     * @return The function
     */
    private Function function;
    /**
     * The mode of the distribution, specified as a latitude-longitude pair
     *
     * @param center The anchor point for the boost
     * @return The geolocation center
     */
    private GeoLocation center;

    private BigDecimal factor;
}
