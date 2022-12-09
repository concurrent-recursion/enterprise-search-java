package co.elasticsearch.enterprisesearch.client.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class NumberArrayField implements Field {
    /**
     * The raw number array value
     * @param raw the value
     * @return the value
     */
    private List<BigDecimal> raw = new ArrayList<>();

    /**
     * Get the values casting each to an Integer.
     * @return the values as an array of Integers
     * @throws ArithmeticException if this has a nonzero fractional part, or will not fit in a int.
     */
    @JsonIgnore
    public List<Integer> getRawInteger() {
        return raw == null ? Collections.emptyList() : raw.stream().map(BigDecimal::intValueExact).collect(Collectors.toList());
    }

    /**
     * Get the values casting each to a Long.
     * @return the values as an array of Longs
     * @throws ArithmeticException if this has a nonzero fractional part, or will not fit in a long.
     */
    @JsonIgnore
    public List<Long> getRawLong() {
        return raw == null ? Collections.emptyList() : raw.stream().map(BigDecimal::longValueExact).collect(Collectors.toList());
    }
}
