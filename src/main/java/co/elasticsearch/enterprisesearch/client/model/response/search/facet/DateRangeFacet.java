package co.elasticsearch.enterprisesearch.client.model.response.search.facet;

import co.elasticsearch.enterprisesearch.client.model.FacetType;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.DateRange;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetRangeValue;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.FacetValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a date range facet
 */
@Getter
@Setter
@Accessors(chain = true)
public class DateRangeFacet implements Facet{
    /**
     * The facet name
     * @param name the name of the facet
     */
    private String name;
    /**
     * The date ranges
     * @param data the date ranges
     */
    private List<DateRange> data = new ArrayList<>();

    @Override
    public FacetType getType() {
        return FacetType.RANGE;
    }

    /**
     * Get the raw FacetValues associated with this DateRangeFacet
     * @return A List of FacetValues representing the date ranges
     */
    public List<FacetValue> getData(){
        return data.stream().map(r -> (FacetRangeValue<OffsetDateTime>) r).collect(Collectors.toList());
    }

    /**
     * Get the date range values from this facet
     * @return The list of Date Ranges
     */
    public List<DateRange> getDateRanges(){
        return data;
    }

}
