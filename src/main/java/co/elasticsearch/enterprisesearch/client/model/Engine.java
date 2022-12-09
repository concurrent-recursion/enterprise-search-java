package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Engine {
    /**
     * The name of the AppSearch Engine
     * @param name the engine name
     * @return the engine name
     */
    private String name;
    /**
     * The type of the engine
     * @param type the engine type
     * @return the engine type
     */
    private String type;
}
