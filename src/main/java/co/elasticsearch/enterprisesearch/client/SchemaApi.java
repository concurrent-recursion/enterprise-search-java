package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.schemas.Schema;

public interface SchemaApi {
    Schema getSchema(String engineName);
    Schema updateSchema(String engineName,Schema schema);
}
