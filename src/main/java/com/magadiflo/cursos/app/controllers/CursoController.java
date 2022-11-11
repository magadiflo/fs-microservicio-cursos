package com.magadiflo.cursos.app.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magadiflo.commons.controllers.CommonController;
import com.magadiflo.cursos.app.models.entity.Curso;
import com.magadiflo.cursos.app.services.ICursoService;

@RestController
public class CursoController extends CommonController<Curso, ICursoService> {

	public CursoController(ICursoService service) {
		super(service);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {
		Optional<Curso> optionalCurso = this.service.findById(id);
		if (optionalCurso.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Curso cursoBD = optionalCurso.get();
		cursoBD.setNombre(curso.getNombre());

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoBD));
	}

}
