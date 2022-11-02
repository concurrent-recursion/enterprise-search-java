package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Sort {
    public static final String SCORE = "_score";
    public static final String ASCENDING = "asc";
    public static final String DESCENDING = "desc";
    private final Map<String,Object> sorts = new HashMap<>();


    public Sort(String field, String direction){
        if(!ASCENDING.equals(direction) && !DESCENDING.equals(direction)){
            throw new IllegalArgumentException("Sort direction '" + direction + "' is invalid. Must be one of ['" + ASCENDING + "','" + DESCENDING + "']");
        }
        sorts.put(field,direction);

    }

    public Sort(String field, GeoLocationSort location){
        sorts.put(field,location);

    }

    @JsonAnyGetter
    public Map<String,Object> getSorts(){
        return sorts;
    }
}
