package co.elasticsearch.enterprisesearch.client.model.request.filter;

import co.elasticsearch.enterprisesearch.client.model.GeoLocation;
import co.elasticsearch.enterprisesearch.client.model.GeolocationRange;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Accessors(chain = true)
public class GeolocationFilter implements Filter, Map.Entry<String, GeolocationRange> {
    private String name;
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


    @Override
    public String getKey() {
        return name;
    }

    @Override
    public GeolocationRange getValue() {
        return new GeolocationRange().setUnit(unit).setTo(to).setFrom(from).setDistance(distance).setCenter(center);
    }

    @Override
    public GeolocationRange setValue(GeolocationRange value) {
        this.to = value.getTo();
        this.from = value.getFrom();
        this.unit = value.getUnit();
        this.center = value.getCenter();
        this.distance = value.getDistance();
        return value;
    }

    public GeolocationFilter setGeolocationRange(GeolocationRange value){
        setValue(value);
        return this;
    }
}
