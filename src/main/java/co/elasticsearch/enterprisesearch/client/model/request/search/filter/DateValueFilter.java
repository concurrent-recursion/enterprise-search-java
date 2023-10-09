package co.elasticsearch.enterprisesearch.client.model.request.search.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a filter on date values
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
public class DateValueFilter implements Filter, FieldFilter {
    /**
     * A shared Date pattern for de/serializing dates according to the RFC 3339 spec
     */

    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SS]XXX";
    /**
     * A shared Date formatter for RFC 3339 dates
     */
    public static final DateTimeFormatter RFC_3339 = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * The field from your schema upon which to apply your filter
     * @param name The field name
     * @return the field name
     */
    @JsonIgnore
    private String name;
    /**
     * The value upon which to filter. The value must be an exact match
     * @param values The Date(s) to match on
     * @return The values
     */
    private List<OffsetDateTime> values = new ArrayList<>();

    //Used for persistence constructor
    private DateValueFilter(){}

    /**
     * Creates a DateValueFilter with the given name
     * @param name The name of the filter
     */
    public DateValueFilter(String name){
        this.name = name;
    }

    /**
     * Creates a DateValueFilter with the given name and values
     * @param name The name of the filter
     * @param values The value(s) to filter on
     */
    public DateValueFilter(String name, OffsetDateTime... values) {
        this(name, Arrays.asList(values));
    }

    /**
     * Creates a DateValueFilter with the given name and values
     * @param name The name of the filter
     * @param values The values to filter on
     */
    public DateValueFilter(String name, List<OffsetDateTime> values) {
        this(name);
        this.values.addAll(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateValueFilter)) return false;
        DateValueFilter that = (DateValueFilter) o;
        return name.equals(that.name) && values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, values);
    }
}
