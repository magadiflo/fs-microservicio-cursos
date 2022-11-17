package com.magadiflo.cursos.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/***
 * @EntityScan({...}), le agregamos el package del entity de la librería commons
 * alumnos y el package de este mismo proyecto, porque allí tenemos al Curso
 *
 */

@EnableFeignClients
@EnableEurekaClient
@EntityScan({ "com.magadiflo.cursos.app.models.entity", "com.magadiflo.commons.examenes.models.entity" })
@SpringBootApplication
public class FsMicroservicioCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsMicroservicioCursosApplication.class, args);
	}

}
