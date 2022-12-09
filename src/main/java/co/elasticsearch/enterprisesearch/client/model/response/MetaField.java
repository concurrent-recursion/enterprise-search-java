package co.elasticsearch.enterprisesearch.client.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class MetaField {
    /**
     * The calculated relevance score for this document
     * @param score the score
     * @return the score
     */
    private BigDecimal score;
}
