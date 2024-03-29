package co.elasticsearch.enterprisesearch.client.model.response.search;

import co.elasticsearch.enterprisesearch.client.model.Engine;
import co.elasticsearch.enterprisesearch.client.model.response.ResponsePage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents metadata from the search response
 */
@Data
@Accessors(chain = true)
public class Meta {
    /**
     * Array of alerts for your deployment. Included with error responses and success responses, so inspect all responses for alerts.
     * @param alerts the alerts
     * @return the alerts
     */
    private List<String> alerts = new ArrayList<>();
    /**
     * Array of warnings for the query. Included with error responses and success responses, so inspect all responses for warnings.
     * @param warnings the warnings
     * @return the warnings
     */
    private List<String> warnings = new ArrayList<>();
    /**
     * The precision of the search
     * @param precision The precision
     * @return the precision
     */
    private Integer precision;
    /**
     * Object delimiting the pagination meta data.
     * @param page the page metadata
     * @return the page metadata
     */
    private ResponsePage page = new ResponsePage();
    /**
     * The engine associated with this search request
     * @param engine the engine
     * @return the engine
     */
    private Engine engine = new Engine();
    /**
     * String ID representing the request. Guaranteed to be unique.
     * @param requestId The request id
     * @return the request id
     */
    @JsonProperty("request_id")
    private String requestId;

}
