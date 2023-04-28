package co.elasticsearch.enterprisesearch.client.model.response;

import co.elasticsearch.enterprisesearch.client.model.response.documents.Meta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class ErrorResponse implements Serializable {
    /**
     * The errors from the response
     * @param errors The errors from the response
     * @return The errors from the response
     */
    private List<String> errors = new ArrayList<>();

    /**
     * The error from the response
     * @param error The error with the response
     * @return The error from the response
     */
    private String error;


}
