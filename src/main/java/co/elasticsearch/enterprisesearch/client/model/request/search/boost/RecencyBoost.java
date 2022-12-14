package co.elasticsearch.enterprisesearch.client.model.request.search.boost;

import co.elasticsearch.enterprisesearch.client.model.request.search.filter.DateValueFilter;
import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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

    /**
     * Type of function to calculate the boost value. Can be linear, exponential, or gaussian.
     *
     * @param function The function to calculate the boost
     * @return The function
     */
    private Function function;

    /**
     * The mode of the distribution, specified as a RFC3339 DateTime
     *
     * @param center The anchor point for the boost
     * @return The datetime center
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateValueFilter.DATE_PATTERN)
    private OffsetDateTime center;

    /**
     * Whether anchoring is based on special value "now"
     * @param useNow true to anchor on "now"
     * @return true if using special value "now", otherwise false
     */
    @JsonIgnore
    private boolean useNow = false;

    private BigDecimal factor;

    /**
     * The center for this boost
     * @return The string representation of the center point
     */
    @JsonGetter
    public String getCenter() {

        if (useNow) {
            return "now";
        } else if (center != null) {
            return DateValueFilter.RFC_3339.format(center);
        } else {
            return null;
        }
    }

}
