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
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Meta.class)
    private Meta meta = new Meta();

    private List<T> results = new ArrayList<>();
}
