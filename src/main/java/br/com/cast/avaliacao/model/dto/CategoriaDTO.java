package br.com.cast.avaliacao.model.dto;

import br.com.cast.avaliacao.model.Categoria;

public class CategoriaDTO {

	private Long idCategoria;
	private Long codigo;
	private String descricao;

	public CategoriaDTO() {
	}

	public CategoriaDTO(Categoria entidade) {
		this.idCategoria = entidade.getIdCategoria();
		this.codigo = entidade.getCodigo();
		this.descricao = entidade.getDescricao();
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
