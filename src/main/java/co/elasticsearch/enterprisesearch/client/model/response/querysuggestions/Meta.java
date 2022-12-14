package co.elasticsearch.enterprisesearch.client.model.response.querysuggestions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Meta {
    /**
     * The request id for this query suggestion
     * @param requestId the request id
     * @return the request id
     */
    @JsonProperty("request_id")
    private String requestId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meta)) return false;
        Meta meta = (Meta) o;
        return Objects.equals(requestId, meta.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }
}
