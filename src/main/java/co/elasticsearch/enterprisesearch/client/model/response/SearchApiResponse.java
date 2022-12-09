package co.elasticsearch.enterprisesearch.client.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchApiResponse<T> {
    /**
     * Object delimiting the results meta data.
     * @param meta the page metadata
     * @return the page metadata
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Meta.class)
    private Meta meta = new Meta();

    /**
     * The documents returned by the request
     * @param results the results
     * @return the results
     */
    private List<T> results = new ArrayList<>();
}
