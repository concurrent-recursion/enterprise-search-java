package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.searchsettings.SearchSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

import static co.elasticsearch.enterprisesearch.client.AppSearchClient.APP_JSON;

@Slf4j
@RequiredArgsConstructor
class SearchSettingsApiImpl implements SearchSettingsApi{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;

    private static final String SEARCH_SETTINGS_PATH = "/api/as/v1/engines/{engineName}/search_settings";


    @Override
    public SearchSettings get(String engineName) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SEARCH_SETTINGS_PATH)).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Getting Search Settings {} ",url);
        Request okRequest = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(),SearchSettings.class);
        }
    }

    @Override
    public SearchSettings update(String engineName, SearchSettings settings) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SEARCH_SETTINGS_PATH)).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Updating Search Settings {} , Settings:{}",url,settings);
        Request okRequest = new Request.Builder()
                .url(url)
                .put(RequestBody.create(objectMapper.writeValueAsBytes(settings), APP_JSON))
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(),SearchSettings.class);
        }
    }

    @Override
    public SearchSettings reset(String engineName) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SEARCH_SETTINGS_PATH + "/reset")).newBuilder().setPathSegment(4, engineName).build();

        log.debug("Resetting Search Settings {}",url);
        Request okRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create("",null))
                .build();

        try (Response response = client.newCall(okRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected Response " + response);
            }
            return objectMapper.readValue(response.body().byteStream(),SearchSettings.class);
        }
    }
}
