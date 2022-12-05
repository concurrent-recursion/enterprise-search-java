package co.elasticsearch.enterprisesearch.client.model.request.range;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
public class NumberRange implements Range {

    public NumberRange(){}
    public NumberRange(BigDecimal from, BigDecimal to){
        this.from = from;
        this.to = to;
    }
    public NumberRange(String from, String to){
        if(from != null){
            this.from = new BigDecimal(from);
        }
        if(to != null){
            this.to = new BigDecimal(to);
        }
    }
    public NumberRange(Integer from, Integer to){
        if(from != null) {
            this.from = new BigDecimal(from);
        }
        if(to != null) {
            this.to = new BigDecimal(to);
        }
    }
    public NumberRange(Long from, Long to){
        if(from != null){
        this.from = new BigDecimal(from);
    }
        if(to != null) {
            this.to = new BigDecimal(to);
        }
    }
    public NumberRange(Float from, Float to){
        if(from != null) {
            this.from = BigDecimal.valueOf(from);
        }
        if(to != null) {
            this.to = BigDecimal.valueOf(to);
        }
    }
    public NumberRange(Double from, Double to){
        if(from != null) {
            this.from = BigDecimal.valueOf(from);
        }
        if(to != null) {
            this.to = BigDecimal.valueOf(to);
        }
    }

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
