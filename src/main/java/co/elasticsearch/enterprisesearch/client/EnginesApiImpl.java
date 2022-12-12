package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.engines.EngineResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EnginesApiImpl implements EnginesApi{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;
    @Override
    public EngineResponse getEngine(String engineName) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + "/api/as/v1/engines/{engineName}")).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Getting Engine {}", url);
        Request okRequest = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(), EngineResponse.class);
        }
    }


}
