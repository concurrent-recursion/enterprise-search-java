package co.elasticsearch.enterprisesearch.client.model.response.documents;

import co.elasticsearch.enterprisesearch.client.model.response.ErrorableResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class DeleteResponse implements ErrorableResponse {
    /**
     * The results
     * @param results the results
     * @return the results
     */
    private List<DeleteResult> documents = new ArrayList<>();

    @JsonIgnore
    public boolean isError(){
        return documents.stream().anyMatch(dr -> !dr.isDeleted());
    }

    public List<DeleteResult> getErrors(){
        return documents.stream().filter(d -> !d.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleteResponse)) return false;
        DeleteResponse that = (DeleteResponse) o;
        return documents.equals(that.documents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documents);
    }
}
