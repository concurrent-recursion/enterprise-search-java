package co.elasticsearch.enterprisesearch.client.model.request.querysuggestions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Document {
    /**
     * The fields contained in the document
     * @param fields the fields in the documents
     * @return the fields in the document
     */
    private List<String> fields = new ArrayList<>();
}
