package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Range {
    private String name;
    private Object from;
    private Object to;

    private String center;

    public String getCenter() {
        return center;
    }

    public Range setCenter(BigDecimal latitude, BigDecimal longitude) {
        DecimalFormat decimalFormat = new DecimalFormat("###.#######");
        this.center = decimalFormat.format(latitude) + ", " + decimalFormat.format(longitude);
        return this;
    }

    public String getName() {
        return name;
    }

    public Range setName(String name) {
        this.name = name;
        return this;
    }

    public Object getFrom() {
        return from;
    }

    public Object getTo() {
        return to;
    }

    public Range setTo(BigDecimal to) {
        this.to = to;
        return this;
    }

    public Range setFrom(BigDecimal from) {
        this.from = from;
        return this;
    }

    public Range setTo(OffsetDateTime to) {
        this.to = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(to);
        return this;
    }

    public Range setFrom(OffsetDateTime from) {
        this.from = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(from);
        return this;
    }
}
