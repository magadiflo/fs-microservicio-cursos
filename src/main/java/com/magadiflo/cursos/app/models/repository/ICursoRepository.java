package com.magadiflo.cursos.app.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.magadiflo.cursos.app.models.entity.Curso;

public interface ICursoRepository extends PagingAndSortingRepository<Curso, Long> {

	/**
	 * JOIN, es para unir las tablas (similar al INNER JOIN de SQL Nativo)
	 * 
	 * FETCH, para poblar el curso con la lista de alumnos, es decir, el atributo
	 * "alumnos" del Entity Cursos será poblado en una sola consulta, sin hacer
	 * consulas por separado
	 */

	// Aquí ya no es a.alumnos, porque definimos alumnos como @Transient, es decir
	// ya no tienen una relación con la tabla, sino ahora, quien tiene relación con
	// la tabla de la BD es cursoAlumnos
	@Query("SELECT c FROM Curso AS c JOIN FETCH c.cursoAlumnos AS ca WHERE ca.alumnoId = ?1")
	Curso findCursoByAlumnoId(Long id);

}
