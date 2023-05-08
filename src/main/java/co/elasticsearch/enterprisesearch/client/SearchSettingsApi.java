package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.searchsettings.SearchSettings;

/**
 * Represents operations that can be executed against the Search Settings API
 */
public interface SearchSettingsApi {
    /**
     * Get the search settings for the given engine
     * @param engineName The name of the engine
     * @return The Search Settings for the given engine
     */
    SearchSettings get(String engineName);

    /**
     * Update the search settings for the given engine
     * @param engineName the name of the engine to update
     * @param settings the new version of the search settings
     * @return The updated search settings
     */
    SearchSettings update(String engineName, SearchSettings settings);

    /**
     * Reset the search settings to the default value
     * @param engineName The name of the engine to update
     * @return The reset search settings
     */
    SearchSettings reset(String engineName);
}
