package br.com.cast.avaliacao.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cast.avaliacao.model.Categoria;
import br.com.cast.avaliacao.model.dto.CategoriaDTO;
import br.com.cast.avaliacao.repository.CategoriaRepository;
import br.com.cast.avaliacao.util.exception.ValidationException;

@Service
public class CategoriaService {

	private static final String CATEGORIA = "Categoria";

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<CategoriaDTO> buscarPorDescricao(String descricao) {
		List<Categoria> list = this.categoriaRepository
				.buscarPorDescricao(StringUtils.isEmpty(descricao) ? StringUtils.EMPTY : descricao.toUpperCase());
		return list.stream().map(CategoriaDTO::new).collect(Collectors.toList());
	}

	public Categoria buscarPorCodigo(Long codigo) {
		Optional<Categoria> objOptional = this.categoriaRepository.findByCodigo(codigo);
		if (!objOptional.isPresent()) {
			throw new ValidationException(codigo, CATEGORIA, "Código");
		}
		return objOptional.get();
	}

	public CategoriaDTO buscarPorId(Long idCategoria) {
		Optional<Categoria> objOptional = this.categoriaRepository.findById(idCategoria);
		if (!objOptional.isPresent()) {
			throw new ValidationException(idCategoria, CATEGORIA);
		}
		return new CategoriaDTO(objOptional.get());
	}

	@Transactional
	public void excluir(Long idCategoria) {
		Optional<Categoria> objOptional = this.categoriaRepository.findById(idCategoria);
		if (!objOptional.isPresent()) {
			throw new ValidationException(idCategoria, CATEGORIA);
		}
		this.categoriaRepository.deleteById(idCategoria);
	}

	@Transactional
	public CategoriaDTO inserir(CategoriaDTO dto) {
		validarCategoria(dto, null);
		Categoria obj = new Categoria();
		obj.setCodigo(dto.getCodigo());
		obj.setDescricao(dto.getDescricao());
		obj = this.categoriaRepository.save(obj);
		return new CategoriaDTO(obj);
	}

	@Transactional
	public CategoriaDTO editar(CategoriaDTO dto) {
		Optional<Categoria> objOptional = this.categoriaRepository.findById(dto.getIdCategoria());
		if (!objOptional.isPresent()) {
			throw new ValidationException(dto.getIdCategoria(), CATEGORIA);
		}
		Categoria obj = objOptional.get();
		validarCategoria(dto, obj);
		obj.setCodigo(dto.getCodigo());
		obj.setDescricao(dto.getDescricao());
		obj = this.categoriaRepository.save(obj);
		return new CategoriaDTO(obj);
	}

	private void validarCategoria(CategoriaDTO dto, Categoria entidade) {
		if (dto.getCodigo() == null) {
			throw new ValidationException("Campo 'Código' obrigatório.");
		}
		if (StringUtils.isEmpty(dto.getDescricao())) {
			throw new ValidationException("Campo 'Descrição' obrigatório.");
		}
		Optional<Categoria> categoriaOptional = this.categoriaRepository.findByCodigo(dto.getCodigo());
		if (categoriaOptional.isPresent()
				&& (entidade == null || (entidade != null && !entidade.getCodigo().equals(dto.getCodigo())))) {
			throw new ValidationException("Código informado já está em uso.");
		}
	}

}
