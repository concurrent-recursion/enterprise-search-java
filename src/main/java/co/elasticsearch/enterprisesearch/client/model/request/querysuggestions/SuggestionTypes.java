package co.elasticsearch.enterprisesearch.client.model.request.querysuggestions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SuggestionTypes {
    /**
     * Represents a document in the results
     * @param documents the documents
     * @return the documents
     */
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = Document.class)
    private Document documents = new Document();
}
