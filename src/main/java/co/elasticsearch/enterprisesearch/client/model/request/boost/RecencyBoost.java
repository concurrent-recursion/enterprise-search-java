package co.elasticsearch.enterprisesearch.client.model.request.boost;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"type","function","center","factor"})
public class RecencyBoost implements Boost{
    private final String type = "proximity";
    private Function function;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime center;
    @JsonIgnore
    private boolean useNow = true;

    private BigDecimal factor;

    @JsonGetter
    public String getCenter(){

        if(useNow){
            return "now";
        }else if (center != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            return formatter.format(center);
        }else{
            return null;
        }
    }

}
