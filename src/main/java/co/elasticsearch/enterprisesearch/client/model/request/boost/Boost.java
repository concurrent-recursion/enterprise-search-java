package co.elasticsearch.enterprisesearch.client.model.request.boost;

import com.fasterxml.jackson.annotation.JsonCreator;

public interface Boost {
    String getType();

    enum Function {
        LINEAR("linear"),EXPONENTIAL("exponential"),GAUSSIAN("gaussian");
        private final String value;
        Function(String value){
            this.value = value;
        }
        @JsonCreator
        public static Function findByValue(String value){
            for(Function o : values()){
                if(o.value.equals(value)){
                    return o;
                }
            }
            return null;
        }
        public String getValue(){
            return this.value;
        }
        @Override
        public String toString(){
            return this.value;
        }
    }

    enum Operation{
        ADD("add"),MULTIPLY("multiply");
        private final String value;
        Operation(String value){
            this.value = value;
        }
        @JsonCreator
        public static Operation findByValue(String value){
            for(Operation o : values()){
                if(o.value.equals(value)){
                    return o;
                }
            }
            return null;
        }
        public String getValue(){
            return this.value;
        }
        @Override
        public String toString(){
            return this.value;
        }
    }
}
