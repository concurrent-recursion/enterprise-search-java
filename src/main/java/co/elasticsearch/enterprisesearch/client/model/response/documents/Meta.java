package co.elasticsearch.enterprisesearch.client.model.response.documents;

import co.elasticsearch.enterprisesearch.client.model.response.ResponsePage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Meta {
    /**
     * The page metadata
     * @param page the page
     * @return the page
     */
    private ResponsePage page;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meta)) return false;
        Meta meta = (Meta) o;
        return page.equals(meta.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page);
    }
}
