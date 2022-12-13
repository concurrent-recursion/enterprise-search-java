package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.sourceengines.SourceEnginesResponse;

import java.io.IOException;
import java.util.List;

public interface SourceEnginesApi {
    SourceEnginesResponse addSourceEngine(String metaEngineName, List<String> sourceEngineNames) throws IOException;
    SourceEnginesResponse removeSourceEngine(String metaEngineName, List<String> sourceEngineNames) throws IOException;

}
