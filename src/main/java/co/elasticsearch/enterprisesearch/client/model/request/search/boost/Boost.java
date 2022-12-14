package co.elasticsearch.enterprisesearch.client.model.request.search.boost;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;

@JsonDeserialize(using = BoostDeserializer.class)
public interface Boost {
    @JsonIgnore
    String getName();

    @JsonIgnore
    Boost setName(String name);

    /**
     * Type of boost
     * @return The boost type
     */
    BoostType getType();

    /**
     * Alter the impact of a boost on the score of a document. Must be between 0 and 10. Defaults to 1.0. A negative factor or fractional factor will not deboost a result. The factor parameter multiplies the value of the boost but does not significantly affect the decay function scale.
     * @return The factor
     */
    BigDecimal getFactor();

    /**
     * Alter the impact of a boost on the score of a document. Must be between 0 and 10. Defaults to 1.0. A negative factor or fractional factor will not deboost a result. The factor parameter multiplies the value of the boost but does not significantly affect the decay function scale.
     * @param factor A value between (inclusive) 0.0 and 10.0
     * @return The boost
     */
    Boost setFactor(BigDecimal factor);

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
