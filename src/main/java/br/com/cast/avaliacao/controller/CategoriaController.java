package br.com.cast.avaliacao.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cast.avaliacao.model.dto.CategoriaDTO;
import br.com.cast.avaliacao.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/listar")
	@Operation(summary = "Retorna as categorias de acordo com a descrição informada")
	public ResponseEntity<List<CategoriaDTO>> buscarPorDescricao(
			@Valid @RequestParam(required = false) String descricao) {
		List<CategoriaDTO> res = this.categoriaService.buscarPorDescricao(descricao);
		return ResponseEntity.ok(res);
	}

	@GetMapping(value = "/{idCategoria}")
	@Operation(summary = "Seleciona uma categoria de acordo com o id informado")
	public ResponseEntity<CategoriaDTO> buscarPorId(@Valid @PathVariable("idCategoria") Long idCategoria) {
		CategoriaDTO res = this.categoriaService.buscarPorId(idCategoria);
		return ResponseEntity.ok(res);
	}

	@PostMapping(value = "/inserir", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Salva uma nova categoria")
	public ResponseEntity<CategoriaDTO> inserir(@Valid @RequestBody CategoriaDTO dto) {
		CategoriaDTO res = this.categoriaService.inserir(dto);
		return ResponseEntity.ok(res);
	}

	@PutMapping(value = "/editar/{idCategoria}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Edita uma categoria")
	public ResponseEntity<CategoriaDTO> editar(@Valid @PathVariable("idCategoria") Long idCategoria,
			@Valid @RequestBody CategoriaDTO dto) {
		dto.setIdCategoria(idCategoria);
		CategoriaDTO res = this.categoriaService.editar(dto);
		return ResponseEntity.ok(res);
	}

	@DeleteMapping(value = "/excluir/{idCategoria}")
	@Operation(summary = "Exclui uma categoria de acordo com o id informado")
	public ResponseEntity<Object> excluir(@Valid @PathVariable("idCategoria") Long idCategoria) {
		this.categoriaService.excluir(idCategoria);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
