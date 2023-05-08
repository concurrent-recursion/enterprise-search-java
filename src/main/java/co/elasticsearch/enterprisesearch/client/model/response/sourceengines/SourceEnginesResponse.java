package co.elasticsearch.enterprisesearch.client.model.response.sourceengines;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Source Engine response
 */
@Getter
@Setter
public class SourceEnginesResponse {
    /**
     * The name of the source engine
     * @param name the name of the source engine
     * @return the name of the source engine
     */
    private String name;
    /**
     * The type of the source engine
     * @param type the type of the source engine
     * @return the type of the source engine
     */
    private String type;
    /**
     * The source engines
     * @param sourceEngines the source engines
     * @return the source engines
     */
    @JsonProperty("source_engines")
    private List<String> sourceEngines = new ArrayList<>();
}
