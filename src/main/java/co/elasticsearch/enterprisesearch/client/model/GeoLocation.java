package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: See https://www.elastic.co/guide/en/app-search/current/api-reference.html#overview-api-references-geolocation
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@JsonDeserialize(using = GeolocationDeserializer.class)
public class GeoLocation {
    static final Pattern LATITUDE_LONGITUDE = Pattern.compile("^([+-]?\\d{1,3}(\\.\\d+)?),\\s?([+-]?\\d{1,3}(\\.\\d+)?)$");
    static final Pattern WELL_KNOWN_TEXT_POINT = Pattern.compile("^POINT \\(([+-]?\\d{1,3}(\\.\\d+)) ([+-]?\\d{1,3}(\\.\\d+))\\)");

    @Getter
    @RequiredArgsConstructor
    public enum Unit {
        MILLIMETERS("mm"),CENTIMETERS("cm"),METERS("m"),KILOMETERS("km"),INCHES("in"),FEET("ft"),YARDS("yd"),MILES("mi");
        @JsonValue
        private final String value;
        @JsonCreator
        public static Unit findByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElse(null);
        }
    }

    @JsonValue
    private final BigDecimal[] center = new BigDecimal[2];
    public GeoLocation(BigDecimal latitude, BigDecimal longitude) {

        validate(latitude,longitude);
        center[0] = longitude;
        center[1] = latitude;


    }

    public GeoLocation(String center){
        Matcher latitudeLongitude = LATITUDE_LONGITUDE.matcher(center);
        Matcher wellKnown = WELL_KNOWN_TEXT_POINT.matcher(center);
        final String latitude;
        final String longitude;
        if(latitudeLongitude.matches()){
            latitude = latitudeLongitude.group(1);
            longitude = latitudeLongitude.group(3);
        }else if(wellKnown.matches()){
            longitude = wellKnown.group(1);
            latitude = wellKnown.group(2);
        }else{
            throw new IllegalStateException("Geohash format is currently unsupported");
        }
        validate(new BigDecimal(latitude),new BigDecimal(longitude));
        this.center[0] = new BigDecimal(longitude);
        this.center[1] = new BigDecimal(latitude);
    }


    private static void validate(BigDecimal latitude, BigDecimal longitude){
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

}
