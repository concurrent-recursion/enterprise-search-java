package co.elasticsearch.enterprisesearch.client.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class TextArrayField implements Field {
    private List<String> raw = new ArrayList<>();
    private String snippet;
}
