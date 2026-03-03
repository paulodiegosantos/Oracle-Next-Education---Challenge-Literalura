package br.com.challenger.literalura.client;

import java.net.URI;
import java.net.http.HttpRequest;

public class ApiRequest {

    private static final String BASE_URL = "https://gutendex.com/books/";

    public HttpRequest buildSearchRequest(String searchTerm) {

        String url = BASE_URL + "?search=" + searchTerm.replace(" ", "%20");

        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();
    }
}