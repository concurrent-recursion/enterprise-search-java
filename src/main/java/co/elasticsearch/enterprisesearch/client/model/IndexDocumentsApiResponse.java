package co.elasticsearch.enterprisesearch.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class IndexDocumentsApiResponse {
    private String id;
    private List<String> errors = new ArrayList<>();
}
