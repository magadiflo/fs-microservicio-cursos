package com.magadiflo.cursos.app.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.magadiflo.commons.alumnos.models.entity.Alumno;

@FeignClient(name = "microservicio-usuarios")
public interface IAlumnoFeignClient {

	@GetMapping(path = "/alumnos-por-curso")
	Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);

}
