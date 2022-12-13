package co.elasticsearch.enterprisesearch.client.model.response.querysuggestions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Meta {
    @JsonProperty("request_id")
    private String requestId;
}
