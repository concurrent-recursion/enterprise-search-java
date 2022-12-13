package co.elasticsearch.enterprisesearch.client.model.response.engines;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EngineResponse {
    private String name;
    private String type;
    private String language;
    @JsonProperty("document_count")
    private Long documentCount;

    @JsonIgnore
    public boolean isError(){
        return !errors.isEmpty();
    }

    private List<String> errors = new ArrayList<>();
}
