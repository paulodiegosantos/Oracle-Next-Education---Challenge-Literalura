package br.com.challenger.literalura.repository;

import br.com.challenger.literalura.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    // Método para verificar se um livro com determinado título e autor já existe
    @Query("""
           select case when count(b) > 0 then true else false end
           from BookEntity b
           where lower(b.title) = lower(:titulo)
             and lower(b.author.name) = lower(:nomeAutor)
           """)
    boolean existsByTitleAndAuthorName(@Param("titulo") String titulo,
                                       @Param("nomeAutor") String nomeAutor);

    // Método para contar livros por idioma (case-insensitive)
    int countByLanguageIgnoreCase(String language);
}