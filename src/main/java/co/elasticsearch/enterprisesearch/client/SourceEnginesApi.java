package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.sourceengines.SourceEnginesResponse;

import java.util.List;

public interface SourceEnginesApi {
    SourceEnginesResponse addSourceEngine(String metaEngineName, List<String> sourceEngineNames);
    SourceEnginesResponse removeSourceEngine(String metaEngineName, List<String> sourceEngineNames);

}
