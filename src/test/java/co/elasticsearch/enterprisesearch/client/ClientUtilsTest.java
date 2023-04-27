package co.elasticsearch.enterprisesearch.client;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.net.URL;

public class ClientUtilsTest {

    private MockWebServer mockWebServer;
    private URL mockServerURL;

    @BeforeAll
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(); //initialise mock web server
        mockServerURL = mockWebServer.url("").url();
    }
    void testFailures(){

    }
}
