package co.elasticsearch.enterprisesearch.client.model.request.boost;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static co.elasticsearch.enterprisesearch.client.model.request.filter.DateValueFilter.DATE_PATTERN;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"type", "function", "center", "factor"})
public class RecencyBoost implements Boost {
    private String name;

    public Boost.BoostType getType() {
        return BoostType.PROXIMITY;
    }

    private Function function;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private OffsetDateTime center;
    @JsonIgnore
    private boolean useNow = true;

    private BigDecimal factor;

    @JsonGetter
    public String getCenter() {

        if (useNow) {
            return "now";
        } else if (center != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            return formatter.format(center);
        } else {
            return null;
        }
    }

}
