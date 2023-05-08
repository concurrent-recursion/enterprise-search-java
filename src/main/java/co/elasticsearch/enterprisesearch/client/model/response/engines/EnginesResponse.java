package co.elasticsearch.enterprisesearch.client.model.response.engines;

import co.elasticsearch.enterprisesearch.client.model.response.ErrorableResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the response from a multi engine request
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EnginesResponse implements ErrorableResponse {

    /**
     * Response metadata containing pagination information
     * @param meta metadata
     * @return response metadata
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Meta.class)
    private Meta meta = new Meta();
    /**
     * The results
     * @param results the results
     * @return the results
     */
    private List<EngineResponse> results = new ArrayList<>();

    /**
     * The errors
     * @param errors the errors
     * @return the errors
     */
    private List<String> errors = new ArrayList<>();

    @JsonIgnore
    public boolean isError(){
        return !errors.isEmpty();
    }
}
