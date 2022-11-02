package co.elasticsearch.enterprisesearch.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class SearchApiResponse<T> {
    private SearchResponseMeta meta = new SearchResponseMeta();
    private List<T> results = new ArrayList<>();
}
