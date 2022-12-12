package co.elasticsearch.enterprisesearch.client;

import lombok.Builder;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Builder
class EnterpriseSearchAuthInterceptor implements Interceptor {

    private final ClientAuthentication auth;
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder intercepted = chain.request().newBuilder();
        if (auth.isTokenAuthentication()) {
            intercepted.header("Authorization", "Bearer " + auth.getToken());
        } else{
            intercepted.header("Authorization", Credentials.basic(auth.getUsername(), auth.getPassword(), StandardCharsets.UTF_8));
        }
        return chain.proceed(intercepted.build());
    }
}
