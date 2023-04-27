package co.elasticsearch.enterprisesearch.client.model.response;

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
    private List<String> errors = new ArrayList<>();

    private String error;
}
