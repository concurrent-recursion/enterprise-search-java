package co.elasticsearch.enterprisesearch.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Analytics {
    private Set<String> tags = new HashSet<>();

    @JsonIgnore
    public Analytics addTag(String tag){
        tags.add(tag);
        return this;
    }
}
