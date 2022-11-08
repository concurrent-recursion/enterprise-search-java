package co.elasticsearch.enterprisesearch.client.model.request.boost;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Accessors(chain = true)
public class RecencyBoost implements Boost{
    private final String type = "proximity";
    private OffsetDateTime center;
    @JsonIgnore
    private boolean useNow = true;

    private Function function;

    private BigDecimal factor;

    @JsonGetter
    public String getCenter(){
        if(center == null){
            return null;
        }
        if(useNow){
            return "now";
        }else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            return formatter.format(center);
        }
    }

}
