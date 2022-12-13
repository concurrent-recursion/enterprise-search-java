package co.elasticsearch.enterprisesearch.client.model.response.documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DeleteResponse {
    private List<DeleteResult> results = new ArrayList<>();

    @JsonIgnore
    public boolean isError(){
        return results.stream().anyMatch(dr -> !dr.isDeleted());
    }
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class DeleteResult{
        private String id;
        private boolean deleted;
    }
}
