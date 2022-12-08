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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@JsonDeserialize(using = GeolocationDeserializer.class)
@ToString
public class GeoLocation {
    static final Pattern LATITUDE_LONGITUDE = Pattern.compile("^([+-]?\\d{1,3}(\\.\\d+)?),\\s?([+-]?\\d{1,3}(\\.\\d+)?)$");
    static final Pattern WELL_KNOWN_TEXT_POINT = Pattern.compile("^POINT \\(([+-]?\\d{1,3}(\\.\\d+)) ([+-]?\\d{1,3}(\\.\\d+))\\)");

    @Getter
    @RequiredArgsConstructor
    public enum Unit {
        MILLIMETERS("mm"), CENTIMETERS("cm"), METERS("m"), KILOMETERS("km"), INCHES("in"), FEET("ft"), YARDS("yd"), MILES("mi");
        @JsonValue
        private final String value;

        @JsonCreator
        public static Unit findByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElse(null);
        }
    }

    @JsonIgnore
    private final BigDecimal latitude;
    @JsonIgnore
    private final BigDecimal longitude;

    @JsonValue
    public BigDecimal[] getCenter() {
        return new BigDecimal[]{longitude, latitude};
    }

    public GeoLocation(BigDecimal latitude, BigDecimal longitude) {
        validate(latitude, longitude);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public GeoLocation(String latitude, String longitude) {
        BigDecimal lat = new BigDecimal(latitude);
        BigDecimal lon = new BigDecimal(longitude);
        validate(lat, lon);
        this.longitude = lon;
        this.latitude = lat;
    }

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
