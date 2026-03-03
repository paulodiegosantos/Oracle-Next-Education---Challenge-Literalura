package br.com.challenger.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "authors")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "death_year")
    private Integer deathYear;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER // Alterado de LAZY para EAGER
    )
    private List<BookEntity> books = new ArrayList<>(); // inicializar para evitar null

    public AuthorEntity() {}

    public AuthorEntity(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }

    public Integer getDeathYear() { return deathYear; }
    public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }

    public List<BookEntity> getBooks() { return books; }
    public void setBooks(List<BookEntity> books) { this.books = books; }

    // Método auxiliar para retornar títulos dos livros do autor
    public List<String> getBookTitles() {
        return books.stream()
                .map(BookEntity::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return name + " (" +
                (birthYear != null ? birthYear : "?") +
                " - " +
                (deathYear != null ? deathYear : "?") +
                ")";
    }
}