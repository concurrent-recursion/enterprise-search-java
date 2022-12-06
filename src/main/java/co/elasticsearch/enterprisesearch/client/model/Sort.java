package co.elasticsearch.enterprisesearch.client.model;

import co.elasticsearch.enterprisesearch.client.model.request.GeoLocationSort;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonSerialize(using = SortSerializer.class)
public class Sort {
    public static final String SCORE = "_score";

    @JsonKey
    private final String name;
    @JsonValue
    private final SortOrder direction;


    @Getter
    @RequiredArgsConstructor
    public enum Direction implements SortOrder{
        ASCENDING("asc"),DESCENDING("desc");
        @JsonValue
        private final String value;

        @JsonCreator
        public static Direction fromValue(String value){
            return Arrays.stream(values()).filter(m -> m.value.equals(value)).findFirst().orElseThrow();
        }

        @Override
        public Direction getOrder() {
            return this;
        }
    }


    public Sort(String field,@NonNull Direction direction){
        this.name = field;
        this.direction = direction;
    }

    public Sort(String field, GeoLocationSort location){
        this.name = field;
        this.direction = location;
    }

    @JsonCreator
    static Sort createSort(ObjectNode node){
        Map.Entry<String, JsonNode> field = node.fields().next();
        if(field.getValue().getNodeType().equals(JsonNodeType.STRING)){
            return new Sort(field.getKey(),Direction.fromValue(field.getValue().asText()));
        }else{
            JsonNode centerNode = field.getValue().get("center");
            JsonNode modeNode = field.getValue().get("mode");
            JsonNode orderNode = field.getValue().get("order");
            final GeoLocationSort geoLocationSort;
            if(centerNode.isArray()){
                geoLocationSort = new GeoLocationSort(new GeoLocation(centerNode.get(0).asText(),centerNode.get(1).asText()));
            }else{
                geoLocationSort = new GeoLocationSort(new GeoLocation(centerNode.asText()));
            }
            geoLocationSort.setMode(GeoLocationSort.Mode.fromValue(modeNode.textValue()));
            geoLocationSort.setOrder(Sort.Direction.fromValue(orderNode.textValue()));
            return new Sort(field.getKey(),geoLocationSort);
        }
    }


}
