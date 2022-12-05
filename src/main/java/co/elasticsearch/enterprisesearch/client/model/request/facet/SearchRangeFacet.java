package co.elasticsearch.enterprisesearch.client.model.request.facet;

import co.elasticsearch.enterprisesearch.client.model.GeoLocation;
import co.elasticsearch.enterprisesearch.client.model.request.range.Range;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize()
public class SearchRangeFacet implements Facet{
    private final String type = "range";
    private String name;
    private GeoLocation center;
    private GeoLocation.Unit unit;
    private List<Range> ranges = new ArrayList<>();

    @JsonGetter
    public BigDecimal[] center(){
        return center == null ? null : center.getCenter();
    }
}
