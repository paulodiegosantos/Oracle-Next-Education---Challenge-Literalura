package br.com.challenger.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;

    @Column(name = "language")
    private String language;

    @Column(name = "download_count")
    private Integer downloadCount;

    public BookEntity() {}

    public BookEntity(String title, AuthorEntity author, String language, Integer downloadCount) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.downloadCount = downloadCount;
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public AuthorEntity getAuthor() { return author; }
    public void setAuthor(AuthorEntity author) { this.author = author; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }

    @Override
    public String toString() {
        return "Book{id=" + id +
                ", title='" + title + '\'' +
                ", author=" + (author != null ? author.getName() : "Desconhecido") +
                ", language=" + language +
                ", downloads=" + downloadCount +
                '}';
    }
}