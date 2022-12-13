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
import okhttp3.*;

import java.util.concurrent.TimeUnit;

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

    private final OkHttpClient client;

    public static Builder builder(String baseUrl) {
        return new Builder(baseUrl);
    }

    @RequiredArgsConstructor
    @Setter
    @Accessors(fluent = true)
    public static class Builder {

        /**
         * The Enterprise Search Base URL. Examples: https://localhost:5601 , https://my-deployment.kb.europe-west1.gcp.cloud.es.io:9243 , etc <br>
         * In the documentation, this is commonly referenced as <code>ENTERPRISE_SEARCH_BASE_URL</code>
         *
         * @param baseUrl The enterprise search base URL
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

        public AppSearchClient build() {
            clientBuilder
                    .addInterceptor(EnterpriseSearchAuthInterceptor.builder().auth(clientAuthentication).build())
                    .build();
            return new AppSearchClient(endpointBase, clientBuilder.build());
        }
    }

    public <T> DocumentsApi<T> documents(Class<T> documentType){
        return new DocumentsApiImpl<>(objectMapper,baseUrl,client,documentType);
    }


    public <T extends ResponseDocument> SearchApi<T> search(Class<T> resultType){
        return new SearchApiImpl<>(objectMapper,baseUrl,client,resultType);
    }

    public EnginesApi engines(){
        return new EnginesApiImpl(objectMapper,baseUrl,client);
    }

    public SchemaApi schemas(){
        return new SchemaApiImpl(objectMapper,baseUrl,client);
    }

    public SearchSettingsApi searchSettings(){
        return new SearchSettingsApiImpl(objectMapper,baseUrl,client);
    }

    public SourceEnginesApi sourceEngines(){
        return new SourceEnginesApiImpl(objectMapper,baseUrl,client);
    }
}
