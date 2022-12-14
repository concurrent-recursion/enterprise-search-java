package co.elasticsearch.enterprisesearch.client.model.response.engines;

import co.elasticsearch.enterprisesearch.client.model.request.engines.CreateEngine;
import co.elasticsearch.enterprisesearch.client.model.response.ErrorableResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
public class EngineResponse implements ErrorableResponse {
    /**
     * The engine name
     * @param name the engine name
     * @return the engine name
     */
    private String name;
    /**
     * The engine type
     * @param type the engine type
     * @return the engine type
     */
    private String type;
    /**
     * The engine language
     * @param language the engine language
     * @return the languages
     */
    private String language;
    /**
     * The count of documents in this engine
     * @param documentCount the count of documents in the engine
     * @return the count of documents in the engine
     */
    @JsonProperty("document_count")
    private Long documentCount;

    /**
     *  A dictionary of settings that will override the default index settings. Currently, the only index setting you
     *  can override is <code>number_of_shards</code>
     */
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    @JsonProperty("index_create_settings_override")
    private Map<String,Object> indexCreateSettingsOverride = new LinkedHashMap<>();


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



    @JsonIgnore
    public boolean isError(){
        return !errors.isEmpty();
    }

    /**
     * The errors
     * @param errors the errors
     * @return the errors
     */
    private List<String> errors = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EngineResponse)) return false;
        EngineResponse that = (EngineResponse) o;
        return name.equals(that.name) && type.equals(that.type) && Objects.equals(language, that.language) && Objects.equals(documentCount, that.documentCount) && Objects.equals(indexCreateSettingsOverride, that.indexCreateSettingsOverride) && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, language, documentCount, indexCreateSettingsOverride, errors);
    }
}
