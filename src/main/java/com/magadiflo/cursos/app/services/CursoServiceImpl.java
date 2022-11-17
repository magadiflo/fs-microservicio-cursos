package com.magadiflo.cursos.app.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magadiflo.commons.alumnos.models.entity.Alumno;
import com.magadiflo.commons.services.CommonServiceImpl;
import com.magadiflo.cursos.app.clients.IAlumnoFeignClient;
import com.magadiflo.cursos.app.clients.IRespuestaFeignClient;
import com.magadiflo.cursos.app.models.entity.Curso;
import com.magadiflo.cursos.app.models.repository.ICursoRepository;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, ICursoRepository> implements ICursoService {

	private final IRespuestaFeignClient respuestaFeignClient;
	private final IAlumnoFeignClient alumnoFeignClient;

	public CursoServiceImpl(ICursoRepository repository, IRespuestaFeignClient respuestaFeignClient,
			IAlumnoFeignClient alumnoFeignClient) {
		super(repository);
		this.respuestaFeignClient = respuestaFeignClient;
		this.alumnoFeignClient = alumnoFeignClient;
	}

	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlumnoId(Long id) {
		return this.repository.findCursoByAlumnoId(id);
	}

	// Aquí el @Transactional ¡NO VA! ya que no es un repository,
	// no es una comunicación con la BD de este microservicio, aunque
	// podría ir, siempre que éste método tenga algún código que haga
	// alguna consulta a la BD, pero así como está hasta el momento, ¡NO!
	// ya que la consulta es hacia otro microservicio
	@Override
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {
		return this.respuestaFeignClient.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
	}

	@Override
	public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids) {
		return this.alumnoFeignClient.obtenerAlumnosPorCurso(ids);
	}

}
