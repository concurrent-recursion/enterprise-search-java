package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.schemas.Schema;

/**
 * Represents operations on the schema API
 */
public interface SchemaApi {
    /**
     * Retrieve the schema for the given engine
     * @param engineName The name of the engine
     * @return The schema for the engine
     */
    Schema getSchema(String engineName);

    /**
     * Update the schema for the given engine
     * @param engineName the name of the engine
     * @param schema the new version of the schema
     * @return the updated schema
     */
    Schema updateSchema(String engineName,Schema schema);
}
