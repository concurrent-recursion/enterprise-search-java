package co.elasticsearch.enterprisesearch.client.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class MetaField {
    private BigDecimal score;
}
