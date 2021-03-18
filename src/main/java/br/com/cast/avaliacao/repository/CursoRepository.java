package br.com.cast.avaliacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cast.avaliacao.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

	@Query(value = "SELECT c FROM Curso c "
			+ "WHERE UPPER(c.descricaoAssunto) LIKE %:descricaoAssunto% "
			+ "ORDER BY c.dataInicio ASC")
	List<Curso> buscarPorDescricaoAssunto(String descricaoAssunto);
	
}
