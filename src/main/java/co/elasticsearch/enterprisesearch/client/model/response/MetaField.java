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
    /**
     * The name of the engine this document belongs to
     * @param engine the engine name
     * @return the engine name
     */
    private String engine;
    /**
     * The document id
     * @param id the document id
     * @return the document id
     */
    private String id;
}
