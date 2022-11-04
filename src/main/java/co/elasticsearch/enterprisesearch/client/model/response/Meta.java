package co.elasticsearch.enterprisesearch.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Meta {
    /**
     * String ID representing the request. Guaranteed to be unique.
     * @param The request id
     */
    @JsonProperty("request_id")
    private String requestId;
    private List<String> warnings = new ArrayList<>();
    private List<String> alerts = new ArrayList<>();
    private Page page = new Page();



}
