package com.magadiflo.cursos.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magadiflo.commons.alumnos.models.entity.Alumno;
import com.magadiflo.commons.controllers.CommonController;
import com.magadiflo.commons.examenes.models.entity.Examen;
import com.magadiflo.cursos.app.models.entity.Curso;
import com.magadiflo.cursos.app.models.entity.CursoAlumno;
import com.magadiflo.cursos.app.services.ICursoService;

@RestController
public class CursoController extends CommonController<Curso, ICursoService> {

	@Value("${config.balanceador.test}")
	private String balanceadorTest;

	public CursoController(ICursoService service) {
		super(service);
	}

	@Override
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Curso> cursos = ((List<Curso>) this.service.findAll()).stream().map(c -> {
			c.getCursoAlumnos().forEach(cursoAlumno -> {
				Alumno alumno = new Alumno();
				// Solo pasamos el id por que en el listar de cursos no necestiamos todo el
				// detalle de los alumnos,
				// solo el id, para tener un count, contar la cant. de alumnos en el curso. El
				// detalle completo de
				// los alumnos va en la página de detalle del curso (método ver())
				alumno.setId(cursoAlumno.getAlumnoId());
				c.addAlumnos(alumno);
			});
			return c;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(cursos);
	}

	@Override
	@GetMapping(path = "/pagina")
	public ResponseEntity<?> listar(Pageable pageable) {
		Page<Curso> cursosPage = this.service.findAll(pageable).map(curso -> {
			curso.getCursoAlumnos().forEach(cursoAlumno -> {
				Alumno alumno = new Alumno();
				alumno.setId(cursoAlumno.getAlumnoId());
				curso.addAlumnos(alumno);
			});
			return curso;
		});

		return ResponseEntity.ok(cursosPage);
	}

	@Override
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id) {
		Optional<Curso> opEntity = this.service.findById(id);
		if (opEntity.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso curso = opEntity.get();
		if (!curso.getCursoAlumnos().isEmpty()) {
			List<Long> ids = curso.getCursoAlumnos().stream().map(cursoAlumno -> cursoAlumno.getAlumnoId())
					.collect(Collectors.toList());
			List<Alumno> alumnos = (List<Alumno>) this.service.obtenerAlumnosPorCurso(ids);
			curso.setAlumnos(alumnos);
		}

		return ResponseEntity.ok(curso);
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
		alumnos.forEach(alumno -> {
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(alumno.getId());
			cursoAlumno.setCurso(cursoBD);

			cursoBD.addCursoAlumno(cursoAlumno);
		});

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoBD));
	}

	@PutMapping(path = "/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
		Optional<Curso> optionalCurso = this.service.findById(id);
		if (optionalCurso.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Curso cursoBD = optionalCurso.get();

		CursoAlumno cursoAlumno = new CursoAlumno();
		// Para cuando se llame al removeCursoAlumno(), se elimine teniendo en cuenta
		// este atributo
		cursoAlumno.setAlumnoId(alumno.getId());

		// Por eso es que en el equals() de CursoAlumno, la comparación se hace con el
		// alumnoId
		cursoBD.removeCrusoAlumno(cursoAlumno);

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

	// Método que usaremos solo para probar cómo es que Spring Cloud Load Balancer
	// selecciona de las distintas instancias la más adecuada
	@GetMapping(path = "/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> response = new HashMap<>();
		response.put("balanceador", this.balanceadorTest);
		response.put("cursos", this.service.findAll());

		return ResponseEntity.ok(response);
	}

	@DeleteMapping(path = "/eliminar-alumno/{id}")
	public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id) {
		this.service.eliminarCursoAlumnoPorId(id);
		return ResponseEntity.noContent().build();
	}

}
