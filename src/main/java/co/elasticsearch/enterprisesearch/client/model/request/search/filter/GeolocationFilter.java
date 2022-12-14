package co.elasticsearch.enterprisesearch.client.model.request.search.filter;

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
    /**
     * The field from your schema upon which to apply your filter
     * @param name the field name
     * @return the field name
     */
    private final String name;
    /**
     * The mode of the distribution, specified as a latitude-longitude pair
     * @param center the center
     * @return the center
     */
    private GeoLocation center;
    /**
     * A number representing the distance unit. Is required if from or to is not given.
     * @param distance the distance
     * @return the distance
     */
    private BigDecimal distance;
    /**
     * The base unit of measurement
     * @param unit the unit
     * @return the unit
     */
    private GeoLocation.Unit unit;

    /**
     * Inclusive lower bound of the range. Is required if to is not given.
     * @param from the lower bound
     * @return the lower bound
     */
    private BigDecimal from;
    /**
     * Exclusive upper bound of the range. Is required if from is not given.
     * @param to the upper bound
     * @return the upper bound
     */
    private BigDecimal to;


    /**
     * Set the range
     * @param range The range for this filter
     * @return This filter
     */
    public GeolocationFilter setRange(GeolocationRange range) {
        this.to = range == null ? null : range.getTo();
        this.from = range == null ? null : range.getFrom();
        this.unit = range == null ? null : range.getUnit();
        this.center = range == null ? null : range.getCenter();
        this.distance = range == null ? null : range.getDistance();
        return this;
    }

    /**
     * Get the range for this filter
     * @return The range
     */
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
