package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchRangeFacet {
    private final String type = "range";
    private String name;
    private GeoLocation center;
    private String unit;
    private List<Range> ranges = new ArrayList<>();

    @JsonGetter
    public BigDecimal[] center(){
        return center == null ? null : center.getCenter();
    }
}
