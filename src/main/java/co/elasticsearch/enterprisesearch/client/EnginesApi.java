package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.request.engines.CreateEngine;
import co.elasticsearch.enterprisesearch.client.model.response.engines.EngineResponse;
import co.elasticsearch.enterprisesearch.client.model.response.engines.EnginesResponse;

public interface EnginesApi {
    /**
     * Retrieves an Engine by name.
     * @param engineName The name of the engine
     * @return the engine
     */
    EngineResponse getEngineByName(String engineName);

    /**
     * Retrieves all Engines that the API Key is scoped to access.
     * @param page Optional, the pagination info
     * @return a list of engines
     */
    EnginesResponse listEngines(Page page);

    EngineResponse createEngine(CreateEngine engine);

    boolean deleteEngine(String engineName);
}
