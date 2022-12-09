package co.elasticsearch.enterprisesearch.client.model.response;

import co.elasticsearch.enterprisesearch.client.model.Engine;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonPropertyOrder({"warnings", "request_id", "page", "alerts"})
public class Meta {
    /**
     * String ID representing the request. Guaranteed to be unique.
     * @param requestId The request id
     * @return the request id
     */
    @JsonProperty("request_id")
    private String requestId;

    /**
     * The precision of the search
     * @param precision The precision
     * @return the precision
     */
    private Integer precision;
    /**
     * Array of warnings for the query. Included with error responses and success responses, so inspect all responses for warnings.
     * @param warnings the warnings
     * @return the warnings
     */
    private List<String> warnings = new ArrayList<>();
    /**
     * Array of alerts for your deployment. Included with error responses and success responses, so inspect all responses for alerts.
     * @param alerts the alerts
     * @return the alerts
     */
    private List<String> alerts = new ArrayList<>();
    /**
     * Object delimiting the pagination meta data.
     * @param page the page metadata
     * @return the page metadata
     */
    private Page page = new Page();

    /**
     * The engine associated with this search request
     * @param engine the engine
     * @return the engine
     */
    private Engine engine = new Engine();


}
