package br.com.cast.avaliacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cast.avaliacao.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	@Query(value = "SELECT c FROM Categoria c "
			+ "WHERE UPPER(c.descricao) LIKE %:descricao% "
			+ "ORDER BY c.descricao")
	List<Categoria> buscarPorDescricao(String descricao);
	
	Optional<Categoria> findByCodigo(Long codigo);
	
}
