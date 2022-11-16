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