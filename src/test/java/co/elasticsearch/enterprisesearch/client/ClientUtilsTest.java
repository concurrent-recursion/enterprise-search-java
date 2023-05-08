package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.request.search.SearchRequest;
import co.elasticsearch.enterprisesearch.client.model.response.TestSearchDocument;
import co.elasticsearch.enterprisesearch.client.model.response.search.SearchApiResponse;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

class ClientUtilsTest {

    private AppSearchClient asp;

    private MockWebServer mockWebServer;
    private final String ENGINE_NAME = "testEngine";

    private AppSearchClient buildClient(MockWebServer server){
        String serverUrl = server.url("").url().toString();
        return AppSearchClient
                .builder(serverUrl.substring(0,serverUrl.length()-1))
                .clientAuthentication(ClientAuthentication.withBasicAuth("user","password"))
                .clientBuilder( new OkHttpClient.Builder().connectTimeout(Duration.ofSeconds(5)).readTimeout(Duration.ofSeconds(5)))
                .build();
    }

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(); //initialise mock web server
        asp = buildClient(mockWebServer);
    }

    @AfterEach
    void cleanup() throws IOException {
        mockWebServer.shutdown();
    }
    @Test
    void testSuccess(){
            String mockResponse = TestUtil.readResourceFile("examples/responses/search-response-1.json");
            mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(mockResponse));

            SearchApi<TestSearchDocument> search = asp.search(TestSearchDocument.class);
            SearchApiResponse<TestSearchDocument> results = search.search(ENGINE_NAME,new SearchRequest());
            Assertions.assertEquals(100,results.getResults().size());
    }

    @Test
    void testBadRequest(){
        String mockResponse = "{\"errors\":[\"Invalid page size\"]}";
        mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(mockResponse));

        SearchApi<TestSearchDocument> search = asp.search(TestSearchDocument.class);
        SearchRequest r = new SearchRequest();
        r.getPage().setSize(-1);
        SearchApiResponse<TestSearchDocument> results = search.search(ENGINE_NAME,r);
        Assertions.assertTrue(results.isError());
    }

    @Test
    void testClientAuthFailure(){
        String mockResponse = "{\"error\": \"Invalid credentials\"}";
        mockWebServer.enqueue(new MockResponse().setResponseCode(401).setBody(mockResponse));
        SearchApi<TestSearchDocument> search = asp.search(TestSearchDocument.class);
        SearchRequest r = new SearchRequest();
        Assertions.assertThrows(ElasticServerException.class,() -> search.search(ENGINE_NAME,r));
    }

    @Test
    void testClientUnauthorized(){
        String mockResponse = "{\"errors\":  [\"Unauthorized action.\"]}";
        mockWebServer.enqueue(new MockResponse().setResponseCode(401).setBody(mockResponse));
        SearchApi<TestSearchDocument> search = asp.search(TestSearchDocument.class);
        SearchRequest r = new SearchRequest();
        Assertions.assertThrows(ElasticServerException.class,() -> search.search(ENGINE_NAME,r));
    }


}
