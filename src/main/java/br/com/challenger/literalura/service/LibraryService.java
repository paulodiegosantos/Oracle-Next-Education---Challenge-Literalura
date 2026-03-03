package br.com.challenger.literalura.service;

import br.com.challenger.literalura.client.dto.Book;
import br.com.challenger.literalura.client.dto.Author;
import br.com.challenger.literalura.model.BookEntity;
import br.com.challenger.literalura.model.AuthorEntity;
import br.com.challenger.literalura.repository.BookRepository;
import br.com.challenger.literalura.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public LibraryService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void salvarLivroDTO(Book bookDto) {
        if (bookDto.getAuthors() == null || bookDto.getAuthors().isEmpty()) return;

        // Considera apenas o primeiro autor
        Author authorDto = bookDto.getAuthors().get(0);

        // Tenta buscar o autor existente pelo nome
        AuthorEntity authorEntity = authorRepository.findByName(authorDto.getName())
                .orElseGet(() -> {
                    // Se não existir, cria novo
                    AuthorEntity a = new AuthorEntity();
                    a.setName(authorDto.getName());
                    a.setBirthYear(authorDto.getBirthYear());
                    a.setDeathYear(authorDto.getDeathYear());
                    return authorRepository.save(a);
                });

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setDownloadCount(bookDto.getDownloadCount());
        bookEntity.setLanguage(
                bookDto.getLanguages().isEmpty()
                        ? "Desconhecido"
                        : bookDto.getLanguages().get(0)
        );
        bookEntity.setAuthor(authorEntity);

        // Salvar livro
        bookRepository.save(bookEntity);
    }

    @Transactional(readOnly = true)
    public boolean existeLivro(String titulo, String nomeAutor) {
        if (titulo == null || nomeAutor == null) return false;
        return bookRepository.existsByTitleAndAuthorName(titulo, nomeAutor);
    }

    @Transactional(readOnly = true)
    public void listarLivrosRegistrados() {
        List<BookEntity> livros = bookRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado no banco.");
            return;
        }

        livros.forEach(livro ->
                System.out.println(
                        "\n📚 Livro: " + livro.getTitle() +
                                "\n👤 Autor: " + (livro.getAuthor() != null ? livro.getAuthor().getName() : "Desconhecido") +
                                "\n🌍 Idioma: " + livro.getLanguage() +
                                "\n⬇ Downloads: " + livro.getDownloadCount() +
                                "\n---------------------------"
                )
        );
    }

    @Transactional(readOnly = true)
    public void listarAutoresRegistrados() {
        List<AuthorEntity> autores = authorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado no banco.");
            return;
        }

        // Remover duplicatas pelo ID
        Map<Long, AuthorEntity> autoresUnicos = autores.stream()
                .collect(Collectors.toMap(
                        AuthorEntity::getId,
                        a -> a,
                        (a1, a2) -> a1 // mantém apenas o primeiro registro
                ));

        autoresUnicos.values().forEach(autor -> {
            System.out.println(
                    "\n👤 Autor: " + autor.getName() +
                            "\n📅 Ano de nascimento: " + (autor.getBirthYear() != null ? autor.getBirthYear() : "?") +
                            "\n📅 Ano de falecimento: " + (autor.getDeathYear() != null ? autor.getDeathYear() : "?") +
                            "\n---------------------------"
            );
        });
    }

    @Transactional(readOnly = true)
    public void listarAutoresVivosEmAno(int ano) {
        List<AuthorEntity> autores = authorRepository.findAutoresVivosEmAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado em " + ano);
            return;
        }

        autores.forEach(a -> {
            System.out.println(
                    "\n👤 Autor: " + a.getName() +
                            "\n📅 Ano de nascimento: " + (a.getBirthYear() != null ? a.getBirthYear() : "?") +
                            "\n📅 Ano de falecimento: " + (a.getDeathYear() != null ? a.getDeathYear() : "?") +
                            "\n---------------------------"
            );
        });
    }

    @Transactional(readOnly = true)
    public void exibirQuantidadeLivrosPorIdioma(String idioma) {
        if (idioma == null || idioma.isBlank()) {
            System.out.println("Idioma inválido.");
            return;
        }

        List<BookEntity> livrosFiltrados = bookRepository.findAll().stream()
                .filter(l -> idioma.equalsIgnoreCase(l.getLanguage()))
                .toList();

        System.out.println("Quantidade de livros em \"" + idioma + "\": " + livrosFiltrados.size());

        if (livrosFiltrados.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma \"" + idioma + "\".");
            return;
        }

        livrosFiltrados.forEach(livro ->
                System.out.println(
                        "\n📚 Livro: " + livro.getTitle() +
                                "\n👤 Autor: " + (livro.getAuthor() != null ? livro.getAuthor().getName() : "Desconhecido") +
                                "\n🌍 Idioma: " + livro.getLanguage() +
                                "\n⬇ Downloads: " + livro.getDownloadCount() +
                                "\n---------------------------"
                )
        );
    }
}