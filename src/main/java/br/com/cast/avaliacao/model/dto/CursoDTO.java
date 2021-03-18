package br.com.cast.avaliacao.model.dto;

import java.time.LocalDate;

import br.com.cast.avaliacao.model.Curso;

public class CursoDTO {

	private Long idCurso;
	private String descricaoAssunto;
	private LocalDate dataInicio;
	private LocalDate dataTermino;
	private Integer quantidadeAlunosTurma;
	private Long codigoCategoria;
	private String descricaoCategoria;

	public CursoDTO() {
	}

	public CursoDTO(Curso entidade) {
		this.idCurso = entidade.getIdCurso();
		this.descricaoAssunto = entidade.getDescricaoAssunto();
		this.dataInicio = entidade.getDataInicio();
		this.dataTermino = entidade.getDataTermino();
		this.quantidadeAlunosTurma = entidade.getQuantidadeAlunosTurma();
		this.codigoCategoria = entidade.getCategoria().getCodigo();
		this.descricaoCategoria = entidade.getCategoria().getDescricao();
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public String getDescricaoAssunto() {
		return descricaoAssunto;
	}

	public void setDescricaoAssunto(String descricaoAssunto) {
		this.descricaoAssunto = descricaoAssunto;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(LocalDate dataTermino) {
		this.dataTermino = dataTermino;
	}

	public Integer getQuantidadeAlunosTurma() {
		return quantidadeAlunosTurma;
	}

	public void setQuantidadeAlunosTurma(Integer quantidadeAlunosTurma) {
		this.quantidadeAlunosTurma = quantidadeAlunosTurma;
	}

	public Long getCodigoCategoria() {
		return codigoCategoria;
	}

	public void setCodigoCategoria(Long codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}

}
