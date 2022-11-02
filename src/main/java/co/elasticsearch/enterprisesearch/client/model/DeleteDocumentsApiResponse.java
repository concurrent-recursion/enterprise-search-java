package co.elasticsearch.enterprisesearch.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeleteDocumentsApiResponse {
    private String id;
    private Boolean deleted;
}
