package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.Engine;
import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.response.engines.EngineResponse;
import co.elasticsearch.enterprisesearch.client.model.response.engines.EnginesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

import static co.elasticsearch.enterprisesearch.client.AppSearchClient.APP_JSON;

@Slf4j
@RequiredArgsConstructor
public class EnginesApiImpl implements EnginesApi{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;

    private static final String ENGINES_PATH = "/api/as/v1/engines";
    @Override
    public EngineResponse getEngineByName(String engineName) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + ENGINES_PATH + "/{engineName}")).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Getting Engine {}", url);
        Request okRequest = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
//            if (!response.isSuccessful() || response.body() == null) {
//                throw new IOException("Unexpected Response " + response);
//            }
            return objectMapper.readValue(response.body().byteStream(), EngineResponse.class);
        }
    }

    @Override
    public EnginesResponse listEngines(Page page) throws IOException {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(baseUrl + ENGINES_PATH)).newBuilder();

        if(page != null) {
            if (page.getCurrent() != null) {
                urlBuilder.addQueryParameter("page[current]", String.valueOf(page.getCurrent()));
            }
            if (page.getSize() != null) {
                urlBuilder.addQueryParameter("page[size]", String.valueOf(page.getSize()));
            }
        }
        Request okRequest = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            return objectMapper.readValue(response.body().byteStream(), EnginesResponse.class);
        }
    }

    @Override
    public EngineResponse createEngine(Engine engine) throws IOException {
        Request okRequest = new Request.Builder()
                .url(HttpUrl.parse(baseUrl + "/api/as/v1/engines/"))
                .post(RequestBody.create(objectMapper.writeValueAsBytes(engine), APP_JSON))
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(), EngineResponse.class);
        }
    }

    @Override
    public boolean deleteEngine(String engineName) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + ENGINES_PATH + "/{engineName}")).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Getting Engine {}", url);
        Request okRequest = new Request.Builder()
                .url(url)
                .delete()
                .build();
        class DeleteResponse{
            boolean deleted;
        }
        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(), DeleteResponse.class).deleted;
        }
    }
}
