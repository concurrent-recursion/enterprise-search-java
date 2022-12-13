package co.elasticsearch.enterprisesearch.client.model.request.querysuggestions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Document {
    private List<String> fields = new ArrayList<>();
}
