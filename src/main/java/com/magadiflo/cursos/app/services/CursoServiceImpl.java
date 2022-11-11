package com.magadiflo.cursos.app.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magadiflo.commons.services.CommonServiceImpl;
import com.magadiflo.cursos.app.models.entity.Curso;
import com.magadiflo.cursos.app.models.repository.ICursoRepository;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, ICursoRepository> implements ICursoService {

	public CursoServiceImpl(ICursoRepository repository) {
		super(repository);
	}

	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlumnoId(Long id) {
		return this.repository.findCursoByAlumnoId(id);
	}

}
