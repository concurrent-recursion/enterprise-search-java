package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.SearchApiRequest;
import co.elasticsearch.enterprisesearch.client.model.response.SearchApiResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Builder
public class SearchClient {
    private static final MediaType APP_JSON = MediaType.parse("application/json");
    private final ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    private final String baseUri;
    private String username;
    private String password;
    private String bearerToken;
    private String engine;
    private Integer readTimeoutMillis = 500;
    private Integer callTimeoutMillis = 5000;
    private OkHttpClient client;



    private final Interceptor authInterceptor = new Interceptor() {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request.Builder intercepted = chain.request().newBuilder();
            if(bearerToken != null){
                intercepted.header("Authorization","Bearer " + bearerToken);
            }else if(username != null && password != null){
                intercepted.header("Authorization",Credentials.basic(username,password));
            }
            return chain.proceed(intercepted.build());
        }
    };

    private OkHttpClient getClient(){
        if(client == null){
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
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUri + "/api/as/v1/engines/{engineName}/search")).newBuilder().setPathSegment(4,engineName).build();
        Request okRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create(objectMapper.writeValueAsBytes(request),APP_JSON))
                .build();

        try (Response response = getClient().newCall(okRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected Response " + response);
            }
            JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, resultClass);
            return objectMapper.readValue(Objects.requireNonNull(response.body()).byteStream(),type);
        }
    }

}
