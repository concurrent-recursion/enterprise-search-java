package co.elasticsearch.enterprisesearch.client.model.request.search.boost;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Represents a Search Boost
 */
@JsonDeserialize(using = BoostDeserializer.class)
public interface Boost {
    /**
     * The name of the boost
     * @return the name
     */
    @JsonIgnore
    String getName();

    /**
     * Set the name of the boost
     * @param name The boost name
     * @return The Boost object
     */
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

    /**
     * The boosting types that are available
     */
    @Getter
    @RequiredArgsConstructor
    enum BoostType {
        /**
         * A functional boost will apply a function to the overall document score. Available on number fields
         */
        FUNCTIONAL("functional"),
        /**
         * A Value Boost will boost the score of a document based on a direct value match. Available on text, number, and date fields.
         */
        VALUE("value"),
        /**
         * A Boost on the difference of a document value and a given value from the center parameter. Available on number and geolocation fields.<br>
         * aka A Proximity Boost, but with a timeframe as the center instead of a coordinate. Use Recency Boosts to boost newer documents.
         */
        PROXIMITY("proximity");

        /**
         * Get the text representation of the enum
         * @return The text value
         */
        @JsonValue
        private final String value;

        /**
         * Get a BoostType by value
         * @param value The value to lookup
         * @return The BoostType, otherwise it will throw an exception
         */
        public static BoostType findByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElse(null);
        }
    }

    /**
     * Decay functions score a document with a function that decays depending on the distance of a numeric field value of the document from a user given origin<br>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-function-score-query.html#_supported_decay_functions">Supported Decay Functions</a>
     */
    @Getter
    @RequiredArgsConstructor
    enum Function {
        /**
         * Linear decay
         */
        LINEAR("linear"),
        /**
         * Exponential decay
         */
        EXPONENTIAL("exponential"),
        /**
         * Normal decay
         */
        GAUSSIAN("gaussian"),
        /**
         * Logarithmic decay
         */
        LOGARITHMIC("logarithmic");

        /**
         * Get the text representation of the enum
         * @return The text value
         */
        @JsonValue
        private final String value;

        /**
         * Get a Function by value
         * @param value The value to lookup
         * @return The Function, otherwise it will throw an exception
         */
        @JsonCreator
        public static Function findByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElseThrow();
        }

    }

    /**
     * The boosting operations that are available
     */
    @Getter
    @RequiredArgsConstructor
    enum Operation {
        /**
         * Add the calculated score
         */
        ADD("add"),
        /**
         * Multiply the calculated score
         */
        MULTIPLY("multiply");

        /**
         * Get the text representation of the enum
         * @return The text value
         */
        @JsonValue
        private final String value;

        /**
         * Get an Operation by value
         * @param value The value to lookup
         * @return The Value, otherwise it will throw an exception
         */
        @JsonCreator
        public static Operation findByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElseThrow();
        }
    }
}
