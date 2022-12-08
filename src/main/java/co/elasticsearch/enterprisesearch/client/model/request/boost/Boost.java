package co.elasticsearch.enterprisesearch.client.model.request.boost;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@JsonDeserialize(using = BoostDeserializer.class)
public interface Boost {
    @JsonIgnore
    String getName();

    @JsonIgnore
    Boost setName(String name);

    @Getter
    @RequiredArgsConstructor
    enum BoostType {
        FUNCTIONAL("functional"), VALUE("value"), PROXIMITY("proximity");
        @JsonValue
        private final String value;

        public static BoostType findByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElse(null);
        }
    }

    @Getter
    @RequiredArgsConstructor
    enum Function {
        LINEAR("linear"), EXPONENTIAL("exponential"), GAUSSIAN("gaussian"), LOGARITHMIC("logarithmic");
        @JsonValue
        private final String value;

        @JsonCreator
        public static Function findByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElseThrow();
        }

    }

    @Getter
    @RequiredArgsConstructor
    enum Operation {
        ADD("add"), MULTIPLY("multiply");
        @JsonValue
        private final String value;

        @JsonCreator
        public static Operation findByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElseThrow();
        }
    }
}
