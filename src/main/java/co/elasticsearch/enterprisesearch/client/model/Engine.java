package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Engine {
    /**
     * Name of the Engine. Can only contain lowercase letters, numbers, and hyphens.
     * @param name name of the engine
     * @return name of the engine
     */
    private String name;
    /**
     * Type of the Engine.
     * @param type type of the engine
     * @return type of the engine
     */
    private String type = "default";
}
