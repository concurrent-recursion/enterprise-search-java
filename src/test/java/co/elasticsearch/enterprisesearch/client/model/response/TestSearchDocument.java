package co.elasticsearch.enterprisesearch.client.model.response;

import co.elasticsearch.enterprisesearch.client.model.response.search.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TestSearchDocument extends ResponseDocument {
    private TextField title;
    private TextArrayField categories;
    private DateField date;
    private NumberField itemNumber;

}
