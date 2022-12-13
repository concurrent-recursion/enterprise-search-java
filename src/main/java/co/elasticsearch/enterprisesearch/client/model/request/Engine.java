package co.elasticsearch.enterprisesearch.client.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Engine {
    private String name;
    private String type = "default";
    private String language;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    @JsonProperty("index_create_settings_override")
    private Map<String,Object> indexCreateSettingsOverride;

    public Engine setIndexSettingOverride(String key, String value){
        indexCreateSettingsOverride.put(key,value);
        return this;
    }
    public Engine setIndexSettingOverride(String key, Integer value){
        indexCreateSettingsOverride.put(key,value);
        return this;
    }
    public Engine setIndexSettingOverride(String key, Boolean value){
        indexCreateSettingsOverride.put(key,value);
        return this;
    }

    public Object getIndexSettingOverride(String key){
        return indexCreateSettingsOverride.get(key);
    }

    public Set<String> getIndexSettingOverrideNames(){
        return indexCreateSettingsOverride.keySet();
    }
}
