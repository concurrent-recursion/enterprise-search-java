package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.searchsettings.SearchSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
class SearchSettingsApiImpl implements SearchSettingsApi{
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final OkHttpClient client;

    private static final String SEARCH_SETTINGS_PATH = "/api/as/v1/engines/{engineName}/search_settings";


    @Override
    public SearchSettings get(String engineName) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SEARCH_SETTINGS_PATH)).newBuilder().setPathSegment(4, engineName).build();
        Request okRequest = new Request.Builder().url(url).get().build();
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,SearchSettings.class);
    }

    @Override
    public SearchSettings update(String engineName, SearchSettings settings) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SEARCH_SETTINGS_PATH)).newBuilder().setPathSegment(4, engineName).build();
        log.debug("Updating Search Settings {} , Settings:{}",url,settings);
        Request okRequest = new Request.Builder().url(url).put(ClientUtils.marshalPayload(objectMapper,settings)).build();
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,SearchSettings.class);
    }

    @Override
    public SearchSettings reset(String engineName){
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + SEARCH_SETTINGS_PATH + "/reset")).newBuilder().setPathSegment(4, engineName).build();
        log.debug("Resetting Search Settings {}",url);
        Request okRequest = new Request.Builder().url(url).post(RequestBody.create("",null)).build();
        return ClientUtils.marshalResponse(client,okRequest,objectMapper,SearchSettings.class);
    }
}
