package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NumberRange implements Range {
    /**
     * Inclusive lower bound of the range. Is required if to is not given.
     *
     * @param from Inclusive lower bound of the range. Is required if to is not given.
     */
    private BigDecimal from;
    /**
     * Exclusive upper bound of the range. Is required if from is not given.
     *
     * @param to Exclusive upper bound of the range
     */
    private BigDecimal to;
    /**
     * Name given to the range
     *
     * @param name Name given to the range
     */
    private String name;

}
