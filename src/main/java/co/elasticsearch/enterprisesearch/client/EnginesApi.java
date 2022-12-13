package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.Engine;
import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.response.engines.EngineResponse;
import co.elasticsearch.enterprisesearch.client.model.response.engines.EnginesResponse;

import java.io.IOException;

public interface EnginesApi {
    /**
     * Retrieves an Engine by name.
     * @param engineName The name of the engine
     * @return the engine
     * @throws IOException if there is an error retrieving the engine
     */
    EngineResponse getEngineByName(String engineName) throws IOException;

    /**
     * Retrieves all Engines that the API Key is scoped to access.
     * @param page Optional, the pagination info
     * @return a list of engines
     * @throws IOException if there is an error retrieving the engine
     */
    EnginesResponse listEngines(Page page) throws IOException;

    EngineResponse createEngine(Engine engine) throws IOException;

    boolean deleteEngine(String engineName) throws IOException;
}
