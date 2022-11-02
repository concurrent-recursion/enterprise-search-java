package co.elasticsearch.enterprisesearch.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ListDocumentsApiResponse<T> {
    private Meta meta = new Meta();
    private List<T> results = new ArrayList<>();
}
