package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.sourceengines.SourceEnginesResponse;

import java.util.List;

/**
 * Represents operations on the source engines API
 */
public interface SourceEnginesApi {
    /**
     * Add source engine(s) to a meta engine
     * @param metaEngineName The name of the meta engine
     * @param sourceEngineNames The name(s) of the source engine(s) that should be added
     * @return The response of the operation
     */
    SourceEnginesResponse addSourceEngine(String metaEngineName, List<String> sourceEngineNames);

    /**
     * Remove source engine(s) from a meta engine
     * @param metaEngineName The name of the meta engine
     * @param sourceEngineNames The name(s) of the source engine(s) that should be removed
     * @return The response of the operation
     */
    SourceEnginesResponse removeSourceEngine(String metaEngineName, List<String> sourceEngineNames);

}
