package co.elasticsearch.enterprisesearch.client.model.response.documents;

import co.elasticsearch.enterprisesearch.client.model.response.ResponsePage;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents metadata from the Response
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Meta implements Serializable {
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
