package co.elasticsearch.enterprisesearch.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeleteDocumentsApiResponse {
    /**
     * The id of the deleted document
     * @param id the document id
     * @return the document id
     */
    private String id;
    /**
     * Whether the document is deleted
     * @param deleted whether the document is deleted
     * @return whether the document is deleted
     */
    private Boolean deleted;
}
