package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.searchsettings.SearchSettings;

public interface SearchSettingsApi {
    SearchSettings get(String engineName);
    SearchSettings update(String engineName, SearchSettings settings);

    SearchSettings reset(String engineName);
}
