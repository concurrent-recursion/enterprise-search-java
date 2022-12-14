package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.request.engines.CreateEngine;
import co.elasticsearch.enterprisesearch.client.model.response.engines.EngineResponse;
import co.elasticsearch.enterprisesearch.client.model.response.engines.EnginesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EnginesApiImpl implements EnginesApi{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;

    private static final String ENGINES_PATH = "/api/as/v1/engines";
    @Override
    public EngineResponse getEngineByName(String engineName) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + ENGINES_PATH + "/{engineName}")).newBuilder().setPathSegment(4, engineName).build();
        Request okRequest = new Request.Builder().url(url).get().build();
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,EngineResponse.class);
    }

    @Override
    public EnginesResponse listEngines(Page page) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(baseUrl + ENGINES_PATH)).newBuilder();

        if(page != null) {
            if (page.getCurrent() != null) {
                urlBuilder.addQueryParameter("page[current]", String.valueOf(page.getCurrent()));
            }
            if (page.getSize() != null) {
                urlBuilder.addQueryParameter("page[size]", String.valueOf(page.getSize()));
            }
        }
        Request okRequest = new Request.Builder().url(urlBuilder.build()).get().build();
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,EnginesResponse.class);
    }

    @Override
    public EngineResponse createEngine(CreateEngine engine) {
        Request okRequest = new Request.Builder()
                .url(HttpUrl.parse(baseUrl + ENGINES_PATH))
                .post(ClientUtils.marshalPayload(objectMapper,engine))
                .build();

        return ClientUtils.marshalResponse(client,okRequest,objectMapper,EngineResponse.class);
    }

    @Override
    public boolean deleteEngine(String engineName) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + ENGINES_PATH + "/{engineName}")).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Getting Engine {}", url);
        Request okRequest = new Request.Builder()
                .url(url)
                .delete()
                .build();

        return ClientUtils.marshalResponse(client,okRequest,objectMapper,DeleteResponse.class).deleted;
    }

    private static class DeleteResponse{
        boolean deleted;
    }
}
