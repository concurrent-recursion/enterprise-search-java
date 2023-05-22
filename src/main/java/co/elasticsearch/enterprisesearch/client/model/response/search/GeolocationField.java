package co.elasticsearch.enterprisesearch.client.model.response.search;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Geolocation field
 */
@Getter
@Setter
@Accessors(chain = true)
public class GeolocationField implements Field {
    static final Pattern LATITUDE_LONGITUDE = Pattern.compile("^[+-]?\\d{1,3}(\\.\\d+)?,[+-]?\\d{1,3}(\\.\\d+)?$");
    static final Pattern WELL_KNOWN_TEXT_POINT = Pattern.compile("^POINT \\(([+-]?\\d{1,3}(\\.\\d+)?) ([+-]?\\d{1,3}(\\.\\d+)?)\\)");
    private BigDecimal[] raw = new BigDecimal[2];

    /**
     * Set the Geolocation data
     * @param geoString The string representation of the Geolocation
     * @return the Geolocation field
     */
    public GeolocationField setRaw(String geoString) {
        Matcher latitudeLongitude = LATITUDE_LONGITUDE.matcher(geoString);
        Matcher wellKnown = WELL_KNOWN_TEXT_POINT.matcher(geoString);
        final String latitude;
        final String longitude;
        if (latitudeLongitude.matches()) {
            String[] latLon = geoString.split(",");
            latitude = latLon[0];
            longitude = latLon[1];
        } else if (wellKnown.matches()) {
            longitude = wellKnown.group(1);
            latitude = wellKnown.group(2);
        } else {
            throw new IllegalStateException("Geohash format is currently unsupported");
        }
        raw = new BigDecimal[]{new BigDecimal(longitude), new BigDecimal(latitude)};
        return this;
    }
}
