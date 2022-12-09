package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.SearchApiRequest;
import co.elasticsearch.enterprisesearch.client.model.response.SearchApiResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Builder
public class SearchClient {
    private static final MediaType APP_JSON = MediaType.parse("application/json");
    private final ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();

    /**
     * The Enterprise Search Base URL. Examples: https://localhost:5601 , https://my-deployment.kb.europe-west1.gcp.cloud.es.io:9243 , etc <br>
     * In the documentation, this is commonly referenced as <code>ENTERPRISE_SEARCH_BASE_URL</code>
     * @param baseUrl The enterprise search base URL
     * @return The enterprise search base url
     */
    private final String baseUrl;

    /**
     * Optional, if using BASIC authentication this is the username
     * @param username the username
     * @return The username
     */
    private String username;
    /**
     * Optional, if using BASIC authentication this is the password
     * @param password the password
     * @return the password
     */
    private String password;
    /**
     * Optional, Either an API Key or Elasticsearch tokens
     * @param bearerToken The API Key, or Elasticsearch token. Should NOT contain "Bearer " prefix
     * @return The API Key or token
     */
    private String bearerToken;
    /**
     * The engine that requests should be sent to
     * @param engine the engine
     * @return the engine
     */
    private String engine;
    /**
     * The timeout waiting to connect to Elasticsearch
     * @param readTimeoutMillis The number of milliseconds to wait, default is 500
     * @return the number of milliseconds to wait
     */
    @Builder.Default
    private Integer readTimeoutMillis = 500;
    /**
     * The timeout waiting for the response to complete from Elasticsearch
     * @param callTimeoutMillis The number of milliseconds to wait, default is 5000
     * @return the number of milliseconds to wait
     */
    @Builder.Default
    private Integer callTimeoutMillis = 5000;


    /**
     * The internal client
     * @param client the client
     * @return the client
     */
    private OkHttpClient client;


    private final Interceptor authInterceptor = new Interceptor() {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request.Builder intercepted = chain.request().newBuilder();
            if (bearerToken != null) {
                intercepted.header("Authorization", "Bearer " + bearerToken);
            } else if (username != null && password != null) {
                intercepted.header("Authorization", Credentials.basic(username, password, StandardCharsets.UTF_8));
            }
            return chain.proceed(intercepted.build());
        }
    };


    private OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(false)
                    .callTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS)
                    .readTimeout(callTimeoutMillis, TimeUnit.MILLISECONDS)
                    .addInterceptor(authInterceptor)
                    .connectionPool(new ConnectionPool(10, 5, TimeUnit.SECONDS))
                    .build();
        }
        return client;
    }

    public <T> SearchApiResponse<T> search(SearchApiRequest request, String engineName, Class<T> resultClass) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + "/api/as/v1/engines/{engineName}/search")).newBuilder().setPathSegment(4, engineName).build();
        Request okRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create(objectMapper.writeValueAsBytes(request), APP_JSON))
                .build();

        try (Response response = getClient().newCall(okRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected Response " + response);
            }
            JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, resultClass);
            return objectMapper.readValue(Objects.requireNonNull(response.body()).byteStream(), type);
        }
    }

}
