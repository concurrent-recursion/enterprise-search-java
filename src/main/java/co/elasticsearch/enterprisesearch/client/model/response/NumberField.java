package co.elasticsearch.enterprisesearch.client.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)

public class NumberField implements Field {
    /**
     * The raw numeric value of the field
     * @param raw the raw value
     * @return the raw value
     */
    private BigDecimal raw;

    /**
     * The raw Long representation of the field
     * @return The Long value
     * @throws ArithmeticException if this has a nonzero fractional part, or will not fit in a long
     */
    @JsonIgnore
    public Long getRawLong() {
        return raw == null ? null : raw.longValueExact();
    }

    /**
     * The raw Integer representation of the field
     * @return The Integer value
     * @throws ArithmeticException if this has a nonzero fractional part, or will not fit in a int
     */
    @JsonIgnore
    public Integer getRawInteger() {
        return raw == null ? null : raw.intValueExact();
    }
}
