package co.elasticsearch.enterprisesearch.client.model.request.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.GeoLocation;
import co.elasticsearch.enterprisesearch.client.model.request.search.range.Range;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"type","name", "center", "unit", "ranges"})
@JsonDeserialize()
public class SearchRangeFacet implements Facet {

    public SearchRangeFacet() {
    }

    public SearchRangeFacet(String fieldName) {
        this.fieldName = fieldName;
    }

    private String fieldName;

    @Override
    public FacetType getType() {
        return FacetType.RANGE;
    }

    /**
     * Name given to facet
     * @param name The name
     * @return the name
     */
    private String name;
    /**
     * The mode of the distribution, specified as a latitude-longitude pair
     * @param center the center
     * @return the center
     */
    private GeoLocation center;
    /**
     * The base unit of measurement
     * @param unit The unit
     * @return the unit
     */
    private GeoLocation.Unit unit;
    /**
     * An array of range objects
     * @param ranges The ranges
     * @return The ranges
     */
    private List<Range> ranges = new ArrayList<>();




    @JsonGetter
    public BigDecimal[] center() {
        return center == null ? null : center.getCenter();
    }
}
