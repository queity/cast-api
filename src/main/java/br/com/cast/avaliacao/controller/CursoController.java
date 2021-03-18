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

import br.com.cast.avaliacao.model.dto.CursoDTO;
import br.com.cast.avaliacao.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/curso")
public class CursoController {

	@Autowired
	private CursoService cursoService;

	@PostMapping(value = "/inserir", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Salva um novo curso")
	public ResponseEntity<CursoDTO> inserir(@Valid @RequestBody CursoDTO dto) {
		CursoDTO res = this.cursoService.inserir(dto);
		return ResponseEntity.ok(res);
	}

	@PutMapping(value = "/editar/{idCurso}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Edita um curso")
	public ResponseEntity<CursoDTO> editar(@Valid @PathVariable("idCurso") Long idCurso,
			@Valid @RequestBody CursoDTO dto) {
		dto.setIdCurso(idCurso);
		CursoDTO res = this.cursoService.editar(dto);
		return ResponseEntity.ok(res);
	}

	@DeleteMapping(value = "/excluir/{idCurso}")
	@Operation(summary = "Exclui um curso de acordo com o id informado")
	public ResponseEntity<Object> excluir(@Valid @PathVariable("idCurso") Long idCurso) {
		this.cursoService.excluir(idCurso);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping(value = "/{idCurso}")
	@Operation(summary = "Seleciona um curso de acordo com o id informado")
	public ResponseEntity<CursoDTO> buscarPorId(@Valid @PathVariable("idCurso") Long idCurso) {
		CursoDTO res = this.cursoService.buscarPorId(idCurso);
		return ResponseEntity.ok(res);
	}

	@GetMapping(value = "/listar")
	@Operation(summary = "Lista os cursos de acordo com a descrição informada")
	public ResponseEntity<List<CursoDTO>> buscarPorDescricao(@Valid @RequestParam(required = false) String descricao) {
		List<CursoDTO> res = this.cursoService.buscarPorDescricaoAssunto(descricao);
		return ResponseEntity.ok(res);
	}

}
