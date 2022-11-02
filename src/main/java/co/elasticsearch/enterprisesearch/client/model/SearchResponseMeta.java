package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class SearchResponseMeta extends Meta{
    private List<String> warnings = new ArrayList<>();
    private List<String> alerts = new ArrayList<>();
    private Integer precision;
    private Engine engine = new Engine();
    @JsonProperty("request_id")
    private String requestId;
}
