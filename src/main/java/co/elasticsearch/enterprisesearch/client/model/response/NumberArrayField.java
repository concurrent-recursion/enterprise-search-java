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
    private List<BigDecimal> raw = new ArrayList<>();

    @JsonIgnore
    public List<Integer> getRawInteger() {
        return raw == null ? Collections.emptyList() : raw.stream().map(BigDecimal::intValueExact).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Long> getRawLong() {
        return raw == null ? Collections.emptyList() : raw.stream().map(BigDecimal::longValueExact).collect(Collectors.toList());
    }
}
