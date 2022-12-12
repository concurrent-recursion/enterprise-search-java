package co.elasticsearch.enterprisesearch.client.model.response.engines;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EngineResponse {
    private String name;
    private String type;
    private String language;
    @JsonProperty("document_count")
    private Long documentCount;
}
