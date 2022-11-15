package co.elasticsearch.enterprisesearch.client.model.request.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DateValueFilter implements Filter {
    @JsonValue
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    private List<OffsetDateTime> values = new ArrayList<>();
}
