package com.magadiflo.cursos.app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magadiflo.commons.alumnos.models.entity.Alumno;
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

	@PutMapping(path = "/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@PathVariable Long id, @RequestBody List<Alumno> alumnos) {
		Optional<Curso> optionalCurso = this.service.findById(id);
		if (optionalCurso.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Curso cursoBD = optionalCurso.get();
		alumnos.forEach(alumno -> cursoBD.addAlumnos(alumno));

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoBD));
	}

	@PutMapping(path = "/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
		Optional<Curso> optionalCurso = this.service.findById(id);
		if (optionalCurso.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Curso cursoBD = optionalCurso.get();
		cursoBD.removeAlumnos(alumno);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoBD));
	}

}
