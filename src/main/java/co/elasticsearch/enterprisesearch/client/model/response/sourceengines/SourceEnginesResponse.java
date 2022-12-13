package co.elasticsearch.enterprisesearch.client.model.response.sourceengines;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SourceEnginesResponse {
    private String name;
    private String type;
    @JsonProperty("source_engines")
    private List<String> sourceEngines = new ArrayList<>();
}
