package com.magadiflo.cursos.app.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/***
 * De forma automática, esta interfaz se registra como un 
 * componente de Srping, por lo tanto puede ser usado para 
 * ser inyectado en otro componente. Eso gracias a dos anotaciones:
 * 		1° Anotación que habilitamos en la clase principal @EnableFeignClient
 * 		2° Anotación definica en esta interfaz @FeignClient
 * Por lo tanto ya es parte del contenedor de Spring y lo podemos inyectar,
 * ya es un componente, como cualquier repository o cualquier otro
 * componente de Spring o service, etc.
 */

// name, es el microservicio con el que nos queremos comunicar
@FeignClient(name = "microservicio-respuestas")
public interface IRespuestaFeignClient {

	/**
	 * Esta es una implementación usando Feign, es una forma declarativa, muy
	 * similar a como lo usamos en los controladores. Pero en este caso, es para
	 * consumir, es para comunicarnos con estos endpoints.
	 * 
	 * Además, tener en cuenta que Feign ya viene integrado con Balanceo de carga, 
	 * es decir, si tenemos varias instancias de un mismo microservicio que están
	 * escaladas en diferentes servidores o en el mismo, feign va ir a buscar y
	 * seleccionar la mejor instancia disponible y a ese se va a conectar. La
	 * herramienta que usa para ese balanceo de carga es Ribbon que también es 
	 * parte de este ecosistema, de Spring Cloud de Netflix
	 * 
	 * 
	 * 
	 * Mediante este método se hará la petición al microservicio respuestas al
	 * endpoint que se define en este path, que es el mismo que está definido en el
	 * microservicio respuestas
	 */
	@GetMapping(path = "/alumno/{alumnoId}/examenes-respondidos")
	Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId);

}
