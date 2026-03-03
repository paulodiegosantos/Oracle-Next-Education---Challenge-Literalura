package br.com.challenger.literalura.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @JsonAlias({"id"})
    private Integer id;

    @JsonAlias({"title"})
    private String title;

    @JsonAlias({"authors"})
    private List<Author> authors;

    @JsonAlias({"summaries"})
    private List<String> summaries;

    @JsonAlias({"subjects"})
    private List<String> subjects;

    @JsonAlias({"bookshelves"})
    private List<String> bookshelves;

    @JsonAlias({"languages"})
    private List<String> languages;

    @JsonAlias({"copyright"})
    private Boolean copyright;

    @JsonAlias({"media_type"})
    private String mediaType;

    @JsonAlias({"formats"})
    private Map<String, String> formats;

    @JsonAlias({"download_count"})
    private Integer downloadCount;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Author> getAuthors() { return authors; }
    public void setAuthors(List<Author> authors) { this.authors = authors; }

    public List<String> getSummaries() { return summaries; }
    public void setSummaries(List<String> summaries) { this.summaries = summaries; }

    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }

    public List<String> getBookshelves() { return bookshelves; }
    public void setBookshelves(List<String> bookshelves) { this.bookshelves = bookshelves; }

    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }

    public Boolean getCopyright() { return copyright; }
    public void setCopyright(Boolean copyright) { this.copyright = copyright; }

    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public Map<String, String> getFormats() { return formats; }
    public void setFormats(Map<String, String> formats) { this.formats = formats; }

    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }

    // Dentro de Book.java
    public String getAuthorsNames() {
        if (authors == null || authors.isEmpty()) return "Desconhecido";
        return authors.stream().map(Author::getName).reduce((a, b) -> a + ", " + b).orElse("Desconhecido");
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + '\'' +
                ", authors=" + authors +
                ", downloads=" + downloadCount + '}';
    }
}