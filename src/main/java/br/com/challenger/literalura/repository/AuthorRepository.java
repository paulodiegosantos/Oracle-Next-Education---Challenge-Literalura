package br.com.challenger.literalura.repository;

import br.com.challenger.literalura.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    // Query para retornar autores vivos no ano informado, tratando nulos
    @Query("""
        select a from AuthorEntity a
        where (a.birthYear is null or a.birthYear <= :ano)
          and (a.deathYear is null or a.deathYear >= :ano)
        """)
    List<AuthorEntity> findAutoresVivosEmAno(@Param("ano") int ano);

    // Buscar autor pelo nome (opcional, usado para evitar duplicação)
    Optional<AuthorEntity> findByName(String name);
}