package co.elasticsearch.enterprisesearch.client.model;

import co.elasticsearch.enterprisesearch.client.model.request.GeoLocationSort;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
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

    @JsonCreator
    static Sort createSort(ObjectNode node){
        Map.Entry<String, JsonNode> field = node.fields().next();
        if(field.getValue().getNodeType().equals(JsonNodeType.STRING)){
            return new Sort(field.getKey(),Direction.fromValue(field.getValue().asText()));
        }else{
            JsonNode center = field.getValue().get("center");
            if(center.isArray()){
                return new Sort(field.getKey(),new GeoLocationSort(new GeoLocation(new BigDecimal(center.get(0).asText()),new BigDecimal(center.get(1).asText()))));
            }else{
                return new Sort(field.getKey(),new GeoLocationSort(new GeoLocation(center.asText())));
            }
        }
    }

    @JsonAnyGetter
    public Map<String,Object> getSorts(){
        return sorts;
    }




}
