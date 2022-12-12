package co.elasticsearch.enterprisesearch.client.model;

import co.elasticsearch.enterprisesearch.client.model.response.search.Meta;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ListDocumentsApiResponse<T> {
    /**
     * The document metadata
     * @param meta the meta
     * @return the meta
     */
    private Meta meta = new Meta();
    /**
     * The list of documents found
     * @param results the documents
     * @return the documents
     */
    private List<T> results = new ArrayList<>();
}
