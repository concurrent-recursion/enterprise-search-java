package co.elasticsearch.enterprisesearch.client.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class RangeFacet implements Facet{
    private String key;
    private final String type = "range";
    private String name;
    private List<Range> ranges = new ArrayList<>();
}
