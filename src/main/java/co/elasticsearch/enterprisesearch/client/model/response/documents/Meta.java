package co.elasticsearch.enterprisesearch.client.model.response.documents;

import co.elasticsearch.enterprisesearch.client.model.response.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Meta {
    /**
     * The page metadata
     * @para page the page
     * @return the page
     */
    private Page page;
}
