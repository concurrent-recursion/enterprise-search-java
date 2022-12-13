package co.elasticsearch.enterprisesearch.client.model.request.querysuggestions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SuggestionTypes {
    private Document documents = new Document();
}
