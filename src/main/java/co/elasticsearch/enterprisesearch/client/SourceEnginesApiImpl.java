package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.sourceengines.SourceEnginesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static co.elasticsearch.enterprisesearch.client.AppSearchClient.APP_JSON;

@Slf4j
@RequiredArgsConstructor
class SourceEnginesApiImpl implements SourceEnginesApi{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;

    private static final String SOURCE_ENGINES_PATH = "/api/as/v1/engines/{engineName}/source_engines";

    @Override
    public SourceEnginesResponse addSourceEngine(String metaEngineName, List<String> sourceEngineNames) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SOURCE_ENGINES_PATH)).newBuilder().setPathSegment(4, metaEngineName).build();

        log.debug("Adding meta engines {} , source engines:{}",url, sourceEngineNames);
        Request okRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create(objectMapper.writeValueAsBytes(sourceEngineNames), APP_JSON))
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(), SourceEnginesResponse.class);
        }
    }

    @Override
    public SourceEnginesResponse removeSourceEngine(String metaEngineName, List<String> sourceEngineNames) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SOURCE_ENGINES_PATH)).newBuilder().setPathSegment(4, metaEngineName).build();

        log.debug("Removing meta engines {} , source engines:{}",url, sourceEngineNames);
        Request okRequest = new Request.Builder()
                .url(url)
                .delete(RequestBody.create(objectMapper.writeValueAsBytes(sourceEngineNames), APP_JSON))
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(), SourceEnginesResponse.class);
        }
    }
}
