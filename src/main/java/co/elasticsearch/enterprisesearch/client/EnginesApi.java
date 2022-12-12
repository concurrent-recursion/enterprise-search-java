package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.engines.EngineResponse;

import java.io.IOException;

public interface EnginesApi {
    EngineResponse getEngine(String engineName) throws IOException;
}
