package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.schemas.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
class SchemaApiImpl implements SchemaApi{

    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;

    private static final String SCHEMA_PATH = "/api/as/v1/engines/{engineName}/schema";

    @Override
    public Schema getSchema(String engineName) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SCHEMA_PATH)).newBuilder().setPathSegment(4, engineName).build();

        Request okRequest = new Request.Builder().url(url).get().build();
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,Schema.class);
    }

    @Override
    public Schema updateSchema(String engineName, Schema schema) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SCHEMA_PATH)).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Update schema {} fields:{}",schema.getFieldNames().size(), url);
        Request okRequest = new Request.Builder().url(url).post(ClientUtils.marshalPayload(objectMapper,schema))
                .build();
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,Schema.class);
    }
}
