package co.elasticsearch.enterprisesearch.client.model.request.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class NumberRangeFilter implements Filter{
    private BigDecimal from;
    private BigDecimal to;
}
