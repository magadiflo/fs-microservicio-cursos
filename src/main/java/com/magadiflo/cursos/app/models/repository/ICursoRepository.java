package com.magadiflo.cursos.app.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.magadiflo.cursos.app.models.entity.Curso;

public interface ICursoRepository extends CrudRepository<Curso, Long> {

}
