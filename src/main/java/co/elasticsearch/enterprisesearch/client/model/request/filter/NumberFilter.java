package co.elasticsearch.enterprisesearch.client.model.request.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class NumberFilter implements Filter {
    private List<BigDecimal> values = new ArrayList<>();
}
