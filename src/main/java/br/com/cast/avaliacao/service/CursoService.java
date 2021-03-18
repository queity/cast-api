package br.com.cast.avaliacao.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cast.avaliacao.model.Categoria;
import br.com.cast.avaliacao.model.Curso;
import br.com.cast.avaliacao.model.dto.CursoDTO;
import br.com.cast.avaliacao.repository.CursoRepository;
import br.com.cast.avaliacao.util.exception.ValidationException;

@Service
public class CursoService {

	private static final String CURSO = "Curso";

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CategoriaService categoriaService;

	@Transactional
	public CursoDTO inserir(CursoDTO dto) {
		validarCurso(dto, null);
		Curso curso = new Curso();
		curso.setDescricaoAssunto(dto.getDescricaoAssunto());
		curso.setDataInicio(dto.getDataInicio());
		curso.setDataTermino(dto.getDataTermino());
		curso.setQuantidadeAlunosTurma(dto.getQuantidadeAlunosTurma());
		Categoria categoria = this.categoriaService.buscarPorCodigo(dto.getCodigoCategoria());
		curso.setCategoria(categoria);
		curso = this.cursoRepository.save(curso);
		return new CursoDTO(curso);
	}

	@Transactional
	public CursoDTO editar(CursoDTO dto) {
		Optional<Curso> objOptional = this.cursoRepository.findById(dto.getIdCurso());
		if (!objOptional.isPresent()) {
			throw new ValidationException(dto.getIdCurso(), CURSO);
		}
		Curso curso = objOptional.get();
		validarCurso(dto, curso);
		curso.setDescricaoAssunto(dto.getDescricaoAssunto());
		curso.setDataInicio(dto.getDataInicio());
		curso.setDataTermino(dto.getDataTermino());
		curso.setQuantidadeAlunosTurma(dto.getQuantidadeAlunosTurma());
		Categoria categoria = this.categoriaService.buscarPorCodigo(dto.getCodigoCategoria());
		curso.setCategoria(categoria);
		curso = this.cursoRepository.save(curso);
		return new CursoDTO(curso);
	}

	@Transactional
	public void excluir(Long idCurso) {
		Optional<Curso> objOptional = this.cursoRepository.findById(idCurso);
		if (!objOptional.isPresent()) {
			throw new ValidationException(idCurso, CURSO);
		}
		this.cursoRepository.deleteById(idCurso);
	}

	public CursoDTO buscarPorId(Long idCurso) {
		Optional<Curso> objOptional = this.cursoRepository.findById(idCurso);
		if (!objOptional.isPresent()) {
			throw new ValidationException(idCurso, CURSO);
		}
		return new CursoDTO(objOptional.get());
	}

	public List<CursoDTO> buscarPorDescricaoAssunto(String descricao) {
		List<Curso> list = this.cursoRepository.buscarPorDescricaoAssunto(
				StringUtils.isEmpty(descricao) ? StringUtils.EMPTY : descricao.toUpperCase());
		return list.stream().map(CursoDTO::new).collect(Collectors.toList());
	}

	private void validarCurso(CursoDTO dto, Curso entidade) {
		if (StringUtils.isEmpty(dto.getDescricaoAssunto())) {
			throw new ValidationException("Campo 'Descrição do Assunto' obrigatório.");
		}
		if (dto.getDataInicio() == null) {
			throw new ValidationException("Campo 'Data Início' obrigatório.");
		}
		if (dto.getDataTermino() == null) {
			throw new ValidationException("Campo 'Data Término' obrigatório.");
		}
		if (dto.getCodigoCategoria() == null) {
			throw new ValidationException("Campo 'Categoria' obrigatório.");
		}
		if (dto.getDataInicio().isBefore(LocalDate.now())
				&& (entidade == null || (entidade != null && !entidade.getDataInicio().isEqual(dto.getDataInicio())))) {
			throw new ValidationException(
					"Não é permitida a inclusão de cursos com a data de início menor que a data atual.");
		}
		if (dto.getDataTermino().isBefore(dto.getDataInicio())) {
			throw new ValidationException(
					"Não é permitida a inclusão de cursos com a data de termino menor que a data de início.");
		}

		Boolean isExiste = isExistenciaPeriodoNoIntervalo(dto.getDataInicio(), dto.getDataTermino(),
				entidade == null ? null : entidade.getIdCurso());
		if (isExiste) {
			throw new ValidationException("Existe(m) curso(s) planejados(s) dentro do período informado.");
		}
	}

	private Boolean isExistenciaPeriodoNoIntervalo(LocalDate novaDataIni, LocalDate novaDataFim, Long id) {
		List<Curso> listaCurso = this.cursoRepository.findAll();

		if (id != null) {
			listaCurso = listaCurso.stream().filter(reg -> !reg.getIdCurso().equals(id)).collect(Collectors.toList());
		}

		for (Curso c : listaCurso) {
			// verifica se a nova data de entrada existe dentro o periodo
			if (isDentroDoIntervaloExistente(novaDataIni, c.getDataInicio(), c.getDataTermino())) {
				return true;
			}
			// verifica se a nova data de termino existe dentro o periodo
			if (isDentroDoIntervaloExistente(novaDataFim, c.getDataInicio(), c.getDataTermino())) {
				return true;
			}
			// verifica se a nova data inicio é menor do que o periodo existente
			if (novaDataIni.isBefore(c.getDataInicio()) || novaDataIni.isBefore(c.getDataTermino())) {
				// verifica se a nova data de termino é maior ou menor do que a existente e se é
				// maior do que a data de inicio
				if (novaDataFim.isAfter(c.getDataInicio())
						&& (novaDataFim.isBefore(c.getDataTermino()) || novaDataFim.isAfter(c.getDataTermino()))) {
					return true;
				}
			}
		}

		return false;
	}

	private Boolean isDentroDoIntervaloExistente(LocalDate novaData, LocalDate dataIniExistente,
			LocalDate dataFimExistente) {
		if (novaData.isEqual(dataIniExistente)) {
			return true;
		}
		if (novaData.isEqual(dataFimExistente)) {
			return true;
		}
		// verifica se a nova data de entrada existe entre o periodo
		if (novaData.isAfter(dataIniExistente) && novaData.isBefore(dataFimExistente)) {
			return true;
		}
		return false;
	}

}
