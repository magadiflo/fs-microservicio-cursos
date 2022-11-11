package com.magadiflo.cursos.app.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.magadiflo.cursos.app.models.entity.Curso;

public interface ICursoRepository extends CrudRepository<Curso, Long> {
	
	/**
	 * JOIN, es para unir las tablas (similar al INNER JOIN de SQL Nativo)
	 * FETCH, para poblar el curso con la lista de alumnos, es decir, el atributo "alumnos" del 
	 * Entity Cursos será poblado en una sola consulta, sin hacer consulas por separado
	 */
	
	@Query("SELECT c FROM Curso AS c JOIN FETCH c.alumnos AS a WHERE a.id = ?1")
	Curso findCursoByAlumnoId(Long id);

}
