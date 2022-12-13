package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.schemas.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

import static co.elasticsearch.enterprisesearch.client.AppSearchClient.APP_JSON;

@Slf4j
@RequiredArgsConstructor
class SchemaApiImpl implements SchemaApi{

    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;

    private static final String SCHEMA_PATH = "/api/as/v1/engines/{engineName}/schema";

    @Override
    public Schema getSchema(String engineName) throws IOException{
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SCHEMA_PATH)).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Get schema {}", url);
        Request okRequest = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(),Schema.class);
        }
    }

    @Override
    public Schema updateSchema(String engineName, Schema schema) throws IOException{
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SCHEMA_PATH)).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Update schema {} fields:{}",schema.getFieldNames().size(), url);
        Request okRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create(objectMapper.writeValueAsBytes(schema), APP_JSON))
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            /*
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }

             */
            return objectMapper.readValue(response.body().byteStream(),Schema.class);
        }
    }
}
