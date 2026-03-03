package br.com.challenger.literalura.client;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.challenger.literalura.client.dto.Book;
import br.com.challenger.literalura.client.dto.BookResponse;
import br.com.challenger.literalura.client.dto.Author;

public class ApiService {

    private final ApiClient apiClient;

    public ApiService() {
        this.apiClient = new ApiClient();
    }

    // --- Método que retorna JSON bruto da API ---
    public String fetchBooks(String searchTerm) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://gutendex.com/books/?search=" + searchTerm.replace(" ", "%20")))
                    .GET()
                    .build();

            HttpResponse<String> response = apiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("URL construída: " + request.uri());
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("JSON retornado: " + response.body());

            return response.body();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar livros na API", e);
        }
    }

    // --- Método que mapeia o JSON para objetos Java ---
    public BookResponse fetchBooksAsObject(String searchTerm) {
        String json = fetchBooks(searchTerm); // pega a String JSON da API

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, BookResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear JSON para objeto", e);
        }
    }

    // --- Método que imprime livros e autores de forma legível ---
    public void printBooks(BookResponse response) {
        System.out.println("Total de livros encontrados: " + response.getCount());
        for (Book book : response.getResults()) {
            System.out.println(book); // utiliza o toString() do Book
            if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
                System.out.println("Autores:");
                for (Author author : book.getAuthors()) {
                    System.out.println(" - " + author); // utiliza o toString() do Author
                }
            }
            System.out.println("---------------------------");
        }
    }
}