package co.elasticsearch.enterprisesearch.client.model.request.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RequiredArgsConstructor
public class DateValueFilter implements Filter, Map.Entry<String,List<OffsetDateTime>> {
    //"yyyy-MM-dd'T'HH:mm:ss[.SS]XXX" ?
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SS]XXX";

    @JsonIgnore
    private final String name;
    @JsonIgnore
    private List<OffsetDateTime> values = new ArrayList<>();

    @Override
    public String getKey() {
        return name;
    }

    @Override
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, with = {JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED})
    public List<OffsetDateTime> getValue() {
        return values;
    }

    @Override
    public List<OffsetDateTime> setValue(List<OffsetDateTime> value) {
        this.values = value;
        return value;
    }

    @JsonIgnore
    public List<String> getValuesJson(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return values.stream().map(format::format).collect(Collectors.toList());
    }
}
