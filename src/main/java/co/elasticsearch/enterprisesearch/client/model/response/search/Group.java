package co.elasticsearch.enterprisesearch.client.model.response.search;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group in search results
 * @param <T> the result type contained in the group
 */
@Getter
@Accessors(chain = true)
public class Group<T extends ResponseDocument> {
    @JsonValue
    private List<T> groupedResults = new ArrayList<>();
}
