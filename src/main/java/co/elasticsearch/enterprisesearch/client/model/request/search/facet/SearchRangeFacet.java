package co.elasticsearch.enterprisesearch.client.model.request.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.Geolocation;
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

/**
 * Represents a Search Facet on a range
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"type","name", "center", "unit", "ranges"})
@JsonDeserialize()
public class SearchRangeFacet implements Facet {

    /**
     * Create an empty range facet
     */
    public SearchRangeFacet() {
    }

    /**
     * Create a range facet with a name and empty range
     * @param fieldName The name of the field to facet on
     */
    public SearchRangeFacet(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * The field name of the facet
     * @param fieldName the field name
     * @return the field name
     */
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
    private Geolocation center;
    /**
     * The base unit of measurement
     * @param unit The unit
     * @return the unit
     */
    private Geolocation.Unit unit;
    /**
     * An array of range objects
     * @param ranges The ranges
     * @return The ranges
     */
    private List<Range> ranges = new ArrayList<>();


    /**
     * In the case of a Geolocation facet range, this is the center point of the range
     * @return An array containing [longitude,latitude] of the center point
     */
    @JsonGetter
    public BigDecimal[] center() {
        return center == null ? null : center.getCenter();
    }
}
