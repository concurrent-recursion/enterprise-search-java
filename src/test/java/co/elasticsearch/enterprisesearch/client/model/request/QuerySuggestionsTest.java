package co.elasticsearch.enterprisesearch.client.model.request;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.request.querysuggestions.QuerySuggestionsRequest;
import co.elasticsearch.enterprisesearch.client.model.response.querysuggestions.QuerySuggestion;
import co.elasticsearch.enterprisesearch.client.model.response.querysuggestions.QuerySuggestionsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuerySuggestionsTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void serializeRequest() throws JsonProcessingException {
        QuerySuggestionsRequest request = new QuerySuggestionsRequest().setQuery("car").setSize(4);
        request.getTypes().getDocuments().setFields(List.of("title","states"));
        String json = objectMapper.writeValueAsString(request);
        assertEquals("{\"query\":\"car\",\"types\":{\"documents\":{\"fields\":[\"title\",\"states\"]}},\"size\":4}",json);
    }

    @Test
    void deserializeResponse() throws JsonProcessingException{
        String json = TestUtil.readResourceFile("examples/responses/querysuggestions.json");
        QuerySuggestionsResponse response = new QuerySuggestionsResponse();
        response.getMeta().setRequestId("914f909793379ed5af9379b4401f19be");
        response.getResults().setSuggestions(List.of(new QuerySuggestion("carlsbad"),new QuerySuggestion("carlsbad caverns"),new QuerySuggestion("carolina")));
        assertEquals(response,objectMapper.readValue(json,QuerySuggestionsResponse.class));
    }
}
