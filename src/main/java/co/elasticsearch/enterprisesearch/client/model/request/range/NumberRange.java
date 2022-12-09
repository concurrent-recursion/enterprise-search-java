package co.elasticsearch.enterprisesearch.client.model.request.range;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
@ToString
public class NumberRange implements Range<BigDecimal> {

    public NumberRange() {
    }

    public NumberRange(BigDecimal from, BigDecimal to) {
        this.from = from;
        this.to = to;
    }

    public NumberRange(String from, String to) {
        if (from != null) {
            this.from = new BigDecimal(from);
        }
        if (to != null) {
            this.to = new BigDecimal(to);
        }
    }

    public NumberRange(Integer from, Integer to) {
        if (from != null) {
            this.from = new BigDecimal(from);
        }
        if (to != null) {
            this.to = new BigDecimal(to);
        }
    }

    public NumberRange(Long from, Long to) {
        if (from != null) {
            this.from = new BigDecimal(from);
        }
        if (to != null) {
            this.to = new BigDecimal(to);
        }
    }

    public NumberRange(Float from, Float to) {
        if (from != null) {
            this.from = BigDecimal.valueOf(from);
        }
        if (to != null) {
            this.to = BigDecimal.valueOf(to);
        }
    }

    public NumberRange(Double from, Double to) {
        if (from != null) {
            this.from = BigDecimal.valueOf(from);
        }
        if (to != null) {
            this.to = BigDecimal.valueOf(to);
        }
    }

    /**
     * Inclusive lower bound of the range. Is required if to is not given.
     * @param from the lower bound
     * @return the lower bound
     */
    private BigDecimal from;
    /**
     * Exclusive upper bound of the range. Is required if from is not given.
     * @param to the upper bound
     * @return the upper bound
     */
    private BigDecimal to;
    /**
     * Name given to the range
     * @param name the range name
     * @return the range name
     */
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberRange)) return false;
        NumberRange that = (NumberRange) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, name);
    }
}
