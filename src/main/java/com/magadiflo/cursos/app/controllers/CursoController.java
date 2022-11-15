package com.magadiflo.cursos.app.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magadiflo.commons.alumnos.models.entity.Alumno;
import com.magadiflo.commons.controllers.CommonController;
import com.magadiflo.commons.examenes.models.entity.Examen;
import com.magadiflo.cursos.app.models.entity.Curso;
import com.magadiflo.cursos.app.services.ICursoService;

@RestController
public class CursoController extends CommonController<Curso, ICursoService> {

	public CursoController(ICursoService service) {
		super(service);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
		if (result.hasErrors()) {
			return this.validar(result);
		}
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

	@GetMapping(path = "/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id) {
		Curso curso = this.service.findCursoByAlumnoId(id);
		if (curso != null) {
			List<Long> examenesIds = (List<Long>) this.service.obtenerExamenesIdsConRespuestasAlumno(id);
			List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
				if (examenesIds.contains(examen.getId())) {
					examen.setRespondido(true);
				}
				return examen;
			}).collect(Collectors.toList());
			
			curso.setExamenes(examenes);
		}
		return ResponseEntity.ok(curso);
	}

	@PutMapping(path = "/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@PathVariable Long id, @RequestBody List<Examen> examenes) {
		Optional<Curso> optionalCurso = this.service.findById(id);
		if (optionalCurso.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Curso cursoBD = optionalCurso.get();
		examenes.forEach(cursoBD::addExamen);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoBD));
	}

	@PutMapping(path = "/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@PathVariable Long id, @RequestBody Examen examen) {
		Optional<Curso> optionalCurso = this.service.findById(id);
		if (optionalCurso.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Curso cursoBD = optionalCurso.get();
		cursoBD.removeExamen(examen);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoBD));
	}

}
