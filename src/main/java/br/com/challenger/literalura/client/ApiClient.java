package br.com.challenger.literalura.client;

import java.net.http.HttpClient;
import java.time.Duration;

public class ApiClient {

    private final HttpClient client;

    public ApiClient() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public HttpClient getClient() {
        return client;
    }
}