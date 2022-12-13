package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.searchsettings.SearchSettings;

import java.io.IOException;

public interface SearchSettingsApi {
    SearchSettings get(String engineName) throws IOException;
    SearchSettings update(String engineName, SearchSettings settings) throws IOException;

    SearchSettings reset(String engineName) throws IOException;
}
