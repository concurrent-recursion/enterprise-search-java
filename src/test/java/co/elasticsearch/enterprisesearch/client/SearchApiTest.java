package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.engines.CreateEngine;
import co.elasticsearch.enterprisesearch.client.model.request.search.SearchRequest;
import co.elasticsearch.enterprisesearch.client.model.response.TestSearchDocument;
import co.elasticsearch.enterprisesearch.client.model.response.engines.EngineResponse;
import co.elasticsearch.enterprisesearch.client.model.response.search.SearchApiResponse;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.NumberRangeFacet;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.TextValueFacet;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

class SearchApiTest {
    private MockWebServer server;
    private AppSearchClient asp;
    private final String ENGINE_NAME = "testEngine";


    private AppSearchClient buildClient(MockWebServer server){
        String serverUrl = server.url("").url().toString();
        return AppSearchClient
                .builder(serverUrl.substring(0,serverUrl.length()-1))
                .clientAuthentication(ClientAuthentication.withBearerAuth("nope"))
                .clientBuilder( new OkHttpClient.Builder().connectTimeout(Duration.ofSeconds(5)).readTimeout(Duration.ofSeconds(5)))
                .build();
    }

    @BeforeEach
    void startWebserver() throws Exception{
        server = new MockWebServer();
        server.start();
        asp = buildClient(server);
    }

    @AfterEach
    void cleanup() throws IOException {
        server.shutdown();
    }


    void testSearch(){
        SearchApiResponse<TestSearchDocument> response = asp.search(TestSearchDocument.class).search("my-test-enging",new SearchRequest());
        Optional<TextValueFacet> topFiveStates =  response.getFacetByFieldAndName("state","top-five-states", TextValueFacet.class);


    }
}
