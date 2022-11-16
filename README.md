# Microservicios Spring Cloud Eureka & Angular Full-stack

## Sección 4: Backend: Microservicio Cursos

### 24. Creando microservicio cursos

# ¿Por qué agregamos la dependencia de Spring Cloud Load Balancer?
- Porque en la **sección 10**, en vez de Zuul Server usaremos Spring Cloud Gateway como servidor de enrutador (gateway)
- Además, en este microservicio estamos usando OpenFeign y la documentación oficial menciona 
que OpenFeign agrega tanto Ribbon como Spring Cloud Load Balancer, pero de todas maneras colocaremos 
de forma explícita el Spring Cloud LoadBalancer

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

# ¿Cómo verificamos que se esté dando el balanceo de carga?
- Podemos ejecutar nuevas instancias asignándole valor a la variable de entorno definida en el application.properties
- Luego cuando, para ejecutar el proyecto se deberá seguir la siguiente secuencia:

```
- Click derecho sobre el proyecto / Run AS/ Run configurations...
- Seleccionar el microservicio al que le vayamos a generar una nueva instancia
- Seleccionamos la pestaña Environment
- Click en Add..
	Name: BALANCEADOR_TEST
	Value: Segunda instancia (cualquier texto)
- Click en Apply/ OK / Run
```
- Recordar que por defecto Spring Cloud Load Balancer utiliza el algoritmo **Round Robin** para determinar la instancia a usar.
- Su funcionamiento es sencillo, por cada petición que se realice irá seleccionando secuencialmente las instancias.