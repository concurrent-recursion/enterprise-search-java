package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: See https://www.elastic.co/guide/en/app-search/current/api-reference.html#overview-api-references-geolocation

/**
 * Represents a GeoLocation
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@JsonDeserialize(using = GeolocationDeserializer.class)
@ToString
public class GeoLocation {
    static final Pattern LATITUDE_LONGITUDE = Pattern.compile("^([+-]?\\d{1,3}(\\.\\d+)?),\\s?([+-]?\\d{1,3}(\\.\\d+)?)$");
    static final Pattern WELL_KNOWN_TEXT_POINT = Pattern.compile("^POINT \\(([+-]?\\d{1,3}(\\.\\d+)) ([+-]?\\d{1,3}(\\.\\d+))\\)");

    /**
     * Represents a Geolocation Unit of measurement
     */
    @Getter
    @RequiredArgsConstructor
    public enum Unit {
        /**
         * Millimeters
         */
        MILLIMETERS("mm"),
        /**
         * Centimeters
         */
        CENTIMETERS("cm"),
        /**
         * Meters
         */
        METERS("m"),
        /**
         * Kilometer
         */
        KILOMETERS("km"),
        /**
         * Inch
         */
        INCHES("in"),
        /**
         * Feet
         */
        FEET("ft"),
        /**
         * Yard
         */
        YARDS("yd"),
        /**
         * Mile
         */
        MILES("mi");

        /**
         * The facet value
         * @return The value value
         */
        @JsonValue
        private final String value;

        /**
         * Find the Unit by value
         * @param value The value to lookup
         * @return The Unit that matches the value, otherwise throw an exception
         */
        @JsonCreator
        public static Unit findByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElse(null);
        }
    }

    /**
     * The latitude of the GeoLocation
     * @return The latitude
     */
    @JsonIgnore
    private final BigDecimal latitude;
    /**
     * The longitude of the GeoLocation
     * @return The longitude
     */
    @JsonIgnore
    private final BigDecimal longitude;

    /**
     * The center point of the GeoLocation object
     * @return An array of [latitude, longitude]
     */
    @JsonValue
    public BigDecimal[] getCenter() {
        return new BigDecimal[]{longitude, latitude};
    }

    /**
     * Create a GeoLocation
     * @param latitude The latitude of the geolocation
     * @param longitude The longitude of the geolocation
     */
    public GeoLocation(BigDecimal latitude, BigDecimal longitude) {
        validate(latitude, longitude);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Create a GeoLocation
     * @param latitude The latitude of the geolocation
     * @param longitude The longitude of the geolocation
     */
    public GeoLocation(String latitude, String longitude) {
        BigDecimal lat = new BigDecimal(latitude);
        BigDecimal lon = new BigDecimal(longitude);
        validate(lat, lon);
        this.longitude = lon;
        this.latitude = lat;
    }

    /**
     * Create a GeoLocation
     * @param center The string representation of the GeoLocation. It must be either latitude, longitude or wellknown format
     */
    public GeoLocation(String center) {
        Matcher latitudeLongitude = LATITUDE_LONGITUDE.matcher(center);
        Matcher wellKnown = WELL_KNOWN_TEXT_POINT.matcher(center);
        final String latitudeString;
        final String longitudeString;
        if (latitudeLongitude.matches()) {
            latitudeString = latitudeLongitude.group(1);
            longitudeString = latitudeLongitude.group(3);
        } else if (wellKnown.matches()) {
            longitudeString = wellKnown.group(1);
            latitudeString = wellKnown.group(2);
        } else {
            throw new IllegalArgumentException("Geohash format is currently unsupported");
        }
        validate(new BigDecimal(latitudeString), new BigDecimal(longitudeString));
        this.longitude = new BigDecimal(longitudeString);
        this.latitude = new BigDecimal(latitudeString);
    }


    private static void validate(BigDecimal latitude, BigDecimal longitude) {
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeoLocation)) return false;
        GeoLocation that = (GeoLocation) o;
        return latitude.equals(that.latitude) && longitude.equals(that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
