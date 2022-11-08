package co.elasticsearch.enterprisesearch.client.model.request.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class TextFilter implements Filter {
    private List<String> values = new ArrayList<>();
}
