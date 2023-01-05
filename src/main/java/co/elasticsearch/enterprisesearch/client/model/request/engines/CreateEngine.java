package co.elasticsearch.enterprisesearch.client.model.request.engines;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateEngine {
    /**
     * Name of the Engine. Can only contain lowercase letters, numbers, and hyphens.
     * @param name name of the engine
     * @return name of the engine
     */
    private String name;
    /**
     * Type of the Engine.
     * @param type type of the engine
     * @return type of the engine
     */
    private String type;

    /**
     * The language associated with a given Engine. Will default to Universal, which will return null.
     * To specify a language, see the list of supported languages.
     * @param language null for universal, or the language code for a specific language
     * @return the language
     */
    private String language;

    /**
     *  A dictionary of settings that will override the default index settings. Currently, the only index setting you
     *  can override is <code>number_of_shards</code>
     */
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    @JsonProperty("index_create_settings_override")
    private Map<String,Object> indexCreateSettingsOverride = new LinkedHashMap<>();

    /**
     * Set an index override value
     * @param key The name of the index setting to override
     * @param value The value of the index setting override
     * @return this engine
     */
    public CreateEngine setIndexSettingOverride(String key, String value){
        indexCreateSettingsOverride.put(key,value);
        return this;
    }
    /**
     * Set an index override value
     * @param key The name of the index setting to override
     * @param value The value of the index setting override
     * @return this engine
     */
    public CreateEngine setIndexSettingOverride(String key, Integer value){
        indexCreateSettingsOverride.put(key,value);
        return this;
    }
    /**
     * Set an index override value
     * @param key The name of the index setting to override
     * @param value The value of the index setting override
     * @return this engine
     */
    public CreateEngine setIndexSettingOverride(String key, Boolean value){
        indexCreateSettingsOverride.put(key,value);
        return this;
    }

    /**
     * Get the value of the index override setting
     * @param key The name of the index setting
     * @return The index setting override value
     */
    @JsonIgnore
    public Object getIndexSettingOverride(String key){
        return indexCreateSettingsOverride.get(key);
    }

    /**
     * Get a set of all the index setting override keys
     * @return A set of the index setting keys
     */
    @JsonIgnore
    public Set<String> getIndexSettingOverrideNames(){
        return indexCreateSettingsOverride.keySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateEngine)) return false;
        CreateEngine that = (CreateEngine) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(language, that.language) && Objects.equals(indexCreateSettingsOverride, that.indexCreateSettingsOverride);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, language, indexCreateSettingsOverride);
    }
}
