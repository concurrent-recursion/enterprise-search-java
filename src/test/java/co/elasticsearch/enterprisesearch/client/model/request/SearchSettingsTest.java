package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.request.search.ResultField;
import co.elasticsearch.enterprisesearch.client.model.request.search.SearchField;
import co.elasticsearch.enterprisesearch.client.model.request.search.boost.TextValueBoost;
import co.elasticsearch.enterprisesearch.client.model.response.searchsettings.SearchSettings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchSettingsTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void serialize() throws JsonProcessingException {
        SearchSettings settings = new SearchSettings();

        settings.addSearchField(new SearchField("description").setWeight(new BigDecimal("1")))
                .addSearchField(new SearchField("states").setWeight(new BigDecimal("1")))
                .addSearchField(new SearchField("title").setWeight(new BigDecimal("3")));

        settings.addResultField(new ResultField("id").withRaw())
                .addResultField(new ResultField("title").withRaw().withSnippet(100));

        settings.setPrecision(2);
        settings.setPrecisionEnabled(true);
        String json = objectMapper.writeValueAsString(settings);
        assertEquals("{\"search_fields\":{\"description\":{\"weight\":1},\"states\":{\"weight\":1},\"title\":{\"weight\":3}},\"result_fields\":{\"id\":{\"raw\":{}},\"title\":{\"raw\":{},\"snippet\":{\"size\":100}}},\"boosts\":{},\"precision\":2,\"precision_enabled\":true}",json);
    }

    @Test
    void deserialize() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/searchSettings.json");
        SearchSettings settings = objectMapper.readValue(json,SearchSettings.class);
        assertTrue(settings.getSearchFields().contains(new SearchField("description").setWeight(new BigDecimal("9.2"))));
        assertTrue(settings.getBoosts().contains(new TextValueBoost().setName("world_heritage_site").setFactor(new BigDecimal("9.5")).setValue(List.of("true"))));
        assertTrue(settings.getResults().contains(new ResultField("states").withRaw().withSnippet(20,true)));
        assertEquals(2,settings.getPrecision());
        assertEquals(true,settings.getPrecisionEnabled());
    }
}
