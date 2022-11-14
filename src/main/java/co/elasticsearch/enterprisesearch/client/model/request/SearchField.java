package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchField {
    /**
     * Weight is given between 10 (most relevant) to 1 (least relevant)
     * @param weight A value between 1 and 10 for relevance
     */
    @Min(1)
    @Max(10)
    private Integer weight;
}
