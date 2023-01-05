package co.elasticsearch.enterprisesearch.client.model.request.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Analytics {
    /**
     * Submit tags with the analytics parameter. Tags can be used to enrich each query with unique information. Once added, a tag cannot be removed. Supports arrays.
     * @param tags Array of strings representing the tags you’d like to apply to the query. You may submit up to 16 tags, and each may be up to 64 characters in length.
     * @return the tags
     */
    private Set<String> tags = new HashSet<>();

    @JsonIgnore
    public Analytics addTag(@Max(64) String tag) {
        tags.add(tag);
        return this;
    }

    /**
     * Submit tags with the analytics parameter. Tags can be used to enrich each query with unique information. Once added, a tag cannot be removed. Supports arrays.
     * @param tags Array of strings representing the tags you’d like to apply to the query. You may submit up to 16 tags, and each may be up to 64 characters in length.
     * @return the tags
     */
    public Analytics withTags(String... tags) {
        this.tags = new HashSet<>(Arrays.asList(tags));
        return this;
    }
}
