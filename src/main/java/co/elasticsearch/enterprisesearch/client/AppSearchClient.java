package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.response.search.ResponseDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Represents a client for the AppSearch APIs. Use the static Builder class to construct an instance of this class.
 */
@Slf4j
@RequiredArgsConstructor
public class AppSearchClient {
    static final MediaType APP_JSON = MediaType.parse("application/json");
    private final ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();


    /**
     * The Enterprise Search Base URL. Examples: https://localhost:5601 , https://my-deployment.kb.europe-west1.gcp.cloud.es.io:9243 , etc <br>
     * In the documentation, this is commonly referenced as <code>ENTERPRISE_SEARCH_BASE_URL</code>
     *
     * @param baseUrl The enterprise search base URL
     * @return The enterprise search base url
     */
    private final String baseUrl;

    /**
     * The backing HTTP Client that makes the requests to Enterprise Search
     * @param client The OkHttpClient
     * @return The OkHttpClient
     */
    private final OkHttpClient client;

    /**
     * Create a builder with the given baseUrl
     * @param baseUrl The Enterprise Search Base URL
     * @return The Builder
     */
    public static Builder builder(String baseUrl) {
        return new Builder(baseUrl);
    }

    /**
     * Used to build an instance of the AppSearch Client
     */
    @RequiredArgsConstructor
    @Setter
    @Accessors(fluent = true)
    public static class Builder {

        /**
         * The Enterprise Search Base URL. Examples: https://localhost:5601 , https://my-deployment.kb.europe-west1.gcp.cloud.es.io:9243 , etc <br>
         * In the documentation, this is commonly referenced as <code>ENTERPRISE_SEARCH_BASE_URL</code>
         *
         * @param endpointBase The enterprise search base URL
         * @return The enterprise search base url
         */
        private final String endpointBase;
        /**
         * The authentication method to connect to the engine
         *
         * @param clientAuthentication the authentication method
         * @return The authentication method
         */
        private ClientAuthentication clientAuthentication;

        /**
         * A client builder to customise the internal configuration of the OkHttpClient
         *
         * @param clientBuilder the clientBuilder
         * @return the clientBuilder
         */
        private OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().connectionPool(new ConnectionPool(10, 10, TimeUnit.SECONDS));

        /**
         * Builds the client
         * @return The constructed AppSearchClient
         */
        public AppSearchClient build() {
            clientBuilder
                    .addInterceptor(EnterpriseSearchAuthInterceptor.builder().auth(clientAuthentication).build())
                    .build();
            return new AppSearchClient(endpointBase, clientBuilder.build());
        }
    }

    /**
     * Create an instance of the AppSearch Documents API
     * @param documentType The class that represents the type of documents that are contained in the engine
     * @return A typed DocumentsApi object
     * @param <T> The type of documents contained in the engine
     */
    public <T> DocumentsApi<T> documents(Class<T> documentType){
        return new DocumentsApiImpl<>(objectMapper,baseUrl,client,documentType);
    }

    /**
     * Create an instance of the AppSearch Search API
     * @param resultType The class that represents the engine's schema
     * @return A typed DocumentsApi object
     * @param <T> The type of ResponseDocument that is represented by the engine's schema
     */
    public <T extends ResponseDocument> SearchApi<T> search(Class<T> resultType){
        return new SearchApiImpl<>(objectMapper,baseUrl,client,resultType);
    }

    /**
     * Creates an instance of the AppSearch Engines API
     * @return The EnginesApi
     */
    public EnginesApi engines(){
        return new EnginesApiImpl(objectMapper,baseUrl,client);
    }

    /**
     * Creates an instance of the AppSearch Schema API
     * @return The SchemaApi
     */
    public SchemaApi schemas(){
        return new SchemaApiImpl(objectMapper,baseUrl,client);
    }

    /**
     * Creates an instance of the AppSearch Search Settings API
     * @return The SearchSettingsApi
     */
    public SearchSettingsApi searchSettings(){
        return new SearchSettingsApiImpl(objectMapper,baseUrl,client);
    }

    /**
     * Creates an instance of the AppSearch Source Engines API
     * @return The SourceEnginesApi
     */
    public SourceEnginesApi sourceEngines(){
        return new SourceEnginesApiImpl(objectMapper,baseUrl,client);
    }
}
