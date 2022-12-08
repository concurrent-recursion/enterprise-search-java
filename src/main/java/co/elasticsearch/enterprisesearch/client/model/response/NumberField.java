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
    private BigDecimal raw;

    @JsonIgnore
    public Long getRawLong() {
        return raw == null ? null : raw.longValueExact();
    }

    @JsonIgnore
    public Integer getRawInteger() {
        return raw == null ? null : raw.intValueExact();
    }
}
