package co.elasticsearch.enterprisesearch.client.model.response.documents;

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
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class DeleteResult{
        private String id;
        private boolean deleted;
    }
}
