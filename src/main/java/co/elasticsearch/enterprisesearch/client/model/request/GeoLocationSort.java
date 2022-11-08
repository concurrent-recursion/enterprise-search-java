package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.client.model.Sort;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

//TODO: See https://www.elastic.co/guide/en/app-search/current/api-reference.html#overview-api-references-geolocation
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GeoLocationSort {
    public GeoLocationSort(BigDecimal latitude, BigDecimal longitude) {
        if (latitude.compareTo(BigDecimal.valueOf(-90L)) < 0) {
            throw new IllegalArgumentException("Latitude cannot be less than -90. Received " + latitude);
        } else if (latitude.compareTo(BigDecimal.valueOf(90L)) > 0) {
            throw new IllegalArgumentException("Latitude cannot be more than 90. Received " + latitude);
        }

        if (longitude.compareTo(BigDecimal.valueOf(-180L)) < 0) {
            throw new IllegalArgumentException("Longitude cannot be less than -180. Received " + longitude);
        } else if (longitude.compareTo(BigDecimal.valueOf(180L)) > 0) {
            throw new IllegalArgumentException("Longitude cannot be more than 180. Received " + longitude);
        }

        center[0] = longitude;
        center[1] = latitude;


    }

    private final BigDecimal[] center = new BigDecimal[2];
    private Sort.Direction order;

    private String mode;

    public BigDecimal[] getCenter() {
        return center;
    }

    public Sort.Direction getOrder() {
        return order;
    }

    public GeoLocationSort setMode(String mode) {
        if (!"min".equals(mode) && !"max".equals(mode) && !"median".equals(mode) && !"avg".equals(mode)) {
            throw new IllegalArgumentException(String.format("Invalid mode '%s' must be one of:['min','max','median','avg']", mode));
        }
        this.mode = mode;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public GeoLocationSort setOrder(Sort.Direction order) {
        this.order = order;
        return this;
    }
}
