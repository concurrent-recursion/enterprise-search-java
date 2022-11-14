package co.elasticsearch.enterprisesearch.client.model;

import co.elasticsearch.enterprisesearch.client.model.request.GeoLocationSort;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Sort {
    public static final String SCORE = "_score";
    @Getter
    @RequiredArgsConstructor
    public enum Direction{
        ASCENDING("asc"),DESCENDING("desc");
        @JsonValue
        private final String value;

        @JsonCreator
        public static Direction fromValue(String value){
            return Arrays.stream(values()).filter(m -> m.value.equals(value)).findFirst().orElse(null);
        }
    }
    private final Map<String,Object> sorts = new HashMap<>();

    public Sort(){}


    public Sort(String field,@NonNull Direction direction){
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
