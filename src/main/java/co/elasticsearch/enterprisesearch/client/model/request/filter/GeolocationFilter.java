package co.elasticsearch.enterprisesearch.client.model.request.filter;

import co.elasticsearch.enterprisesearch.client.model.GeoLocation;
import co.elasticsearch.enterprisesearch.client.model.GeolocationRange;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@ToString
public class GeolocationFilter implements Filter {
    private final String name;
    /**
     * The mode of the distribution, specified as a latitude-longitude pair
     */
    private GeoLocation center;
    private BigDecimal distance;
    /**
     * The base unit of measurement: mm, cm, m (meters), km, in, ft, yd, or mi (mile)
     */
    private GeoLocation.Unit unit;


    private BigDecimal from;
    private BigDecimal to;


    public GeolocationFilter setRange(GeolocationRange value) {
        this.to = value == null ? null : value.getTo();
        this.from = value == null ? null : value.getFrom();
        this.unit = value == null ? null : value.getUnit();
        this.center = value == null ? null : value.getCenter();
        this.distance = value == null ? null : value.getDistance();
        return this;
    }

    public GeolocationRange getRange() {
        return new GeolocationRange().setCenter(center).setFrom(from).setTo(to).setUnit(unit).setDistance(distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeolocationFilter)) return false;
        GeolocationFilter that = (GeolocationFilter) o;
        return name.equals(that.name) && center.equals(that.center) && Objects.equals(distance, that.distance) && unit == that.unit && Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, center, distance, unit, from, to);
    }
}
