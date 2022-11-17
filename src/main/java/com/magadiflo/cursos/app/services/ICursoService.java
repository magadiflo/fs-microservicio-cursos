package com.magadiflo.cursos.app.services;

import java.util.List;

import com.magadiflo.commons.alumnos.models.entity.Alumno;
import com.magadiflo.commons.services.ICommonService;
import com.magadiflo.cursos.app.models.entity.Curso;

public interface ICursoService extends ICommonService<Curso> {

	Curso findCursoByAlumnoId(Long id);

	Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);

	Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids);

	void eliminarCursoAlumnoPorId(Long alumnoId);

}
