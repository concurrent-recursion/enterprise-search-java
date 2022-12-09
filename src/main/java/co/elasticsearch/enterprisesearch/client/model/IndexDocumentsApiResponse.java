package co.elasticsearch.enterprisesearch.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class IndexDocumentsApiResponse {
    /**
     * The indexed document's id
     * @param id the document id
     * @return the document id
     */
    private String id;
    /**
     * List of errors, if any, while indexing the document
     * @param errors list of errors
     * @return list of errors
     */
    private List<String> errors = new ArrayList<>();
}
