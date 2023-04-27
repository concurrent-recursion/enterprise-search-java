package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.AppSearchErrorResponseException;
import co.elasticsearch.enterprisesearch.client.model.response.NationalParkStringDocument;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResponse;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class DocumentsApiImplTest {
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

    @Test
    void testDocumentInsertFail() {

        DocumentsApi<NationalParkStringDocument> api = asp.documents(NationalParkStringDocument.class);
        List<NationalParkStringDocument> docs = List.of( new NationalParkStringDocument().setDescription("test").setAcres("32"));

        server.enqueue(new MockResponse().setResponseCode(500).setBody("{\"errors\":[\"Internal Error Occurred\"]}"));
        server.enqueue(new MockResponse().setResponseCode(500).setBody("{\"errors\":[\"Internal Error Occurred\"]}"));
        server.enqueue(new MockResponse().setResponseCode(500).setBody("{\"errors\":[\"Internal Error Occurred\"]}"));

        Assertions.assertThrows(AppSearchErrorResponseException.class,() -> api.index(ENGINE_NAME,docs));

    }

    @Test
    void testInvalidCredentials(){
        DocumentsApi<NationalParkStringDocument> api = asp.documents(NationalParkStringDocument.class);
        List<NationalParkStringDocument> docs = List.of( new NationalParkStringDocument().setDescription("test").setAcres("32"));

        server.enqueue(new MockResponse().setResponseCode(401).setBody("{\"error\": \"You need to sign in before continuing.\"}"));
        server.enqueue(new MockResponse().setResponseCode(401).setBody("{\"error\": \"Invalid credentials\"}"));
        server.enqueue(new MockResponse().setResponseCode(401).setBody("{\"error\": \"You need to sign in before continuing.\"}"));
        try {
            api.index(ENGINE_NAME, docs);
            Assertions.fail();//exception should be thrown, we shouldn't get here
        }catch(AppSearchErrorResponseException e){
            Assertions.assertEquals(401,e.getResponseStatus());
        }
    }

    @Test
    void testDocumentInsertSuccess() {

        DocumentsApi<NationalParkStringDocument> api = asp.documents(NationalParkStringDocument.class);
        List<NationalParkStringDocument> docs = List.of( new NationalParkStringDocument().setDescription("test").setAcres("32"));
        String id = "123456";
        server.enqueue(new MockResponse().setResponseCode(200).setBody("[{\"id\":\"" + id + "\"}]"));

        IndexResponse result =  api.index(ENGINE_NAME,docs);
        Assertions.assertEquals(id,result.getDocuments().get(0).getId());
    }

    @Test
    void timeoutRetries() {

        DocumentsApi<NationalParkStringDocument> api = asp.documents(NationalParkStringDocument.class);
        //api.disableRetries();
        List<NationalParkStringDocument> docs = List.of( new NationalParkStringDocument().setDescription("test").setAcres("32"));
        String id = "123456";

        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE));
        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE));
        assertThrows(ElasticServerException.class, () -> api.index(ENGINE_NAME, docs));

    }
}
