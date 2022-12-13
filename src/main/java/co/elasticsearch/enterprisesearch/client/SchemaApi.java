package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.schemas.Schema;

import java.io.IOException;

public interface SchemaApi {
    Schema getSchema(String engineName) throws IOException;
    Schema updateSchema(String engineName,Schema schema) throws IOException;
}
