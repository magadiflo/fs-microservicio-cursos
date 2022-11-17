package com.magadiflo.cursos.app.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magadiflo.commons.alumnos.models.entity.Alumno;
import com.magadiflo.commons.examenes.models.entity.Examen;

@Entity
@Table(name = "cursos")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nombre;

	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	/**
	 * @Transient, si bien es cierto, en esta bd de MySQL no está la tabla alumnos,
	 * muchos menos un atributo llamado alumnos, es decir, solo será un atributo en
	 * esta clase que será poblada después. Es decir, después vamos a requerir un
	 * listado de alumnos en Curso. Después, cuando necesitemos por Curso el listado
	 * de alumnos, lo tenemos que ir a buscar al microservicio-usuarios, y es ese
	 * microservicio quien realiza la consulta a Postgresql, de esa manera
	 * estaríamos estableciendo una relación distribuida. Ir a buscar a los alumnos
	 * de este curso mediante los Ids y se lo asignamos al curso los objetos
	 * completos de los alumnos
	 */
	// @OneToMany
	@Transient
	private List<Alumno> alumnos;

	@JsonIgnoreProperties(value = { "curso" }, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CursoAlumno> cursoAlumnos;

	@ManyToMany
	private List<Examen> examenes;

	public Curso() {
		this.alumnos = new ArrayList<>();
		this.examenes = new ArrayList<>();
		this.cursoAlumnos = new ArrayList<>();
	}

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public void addAlumnos(Alumno alumno) {
		this.alumnos.add(alumno);
	}

	public void removeAlumnos(Alumno alumno) {
		this.alumnos.remove(alumno);
	}

	public List<Examen> getExamenes() {
		return examenes;
	}

	public void setExamenes(List<Examen> examenes) {
		this.examenes = examenes;
	}

	// Aquí no es necesario la relación inversa. En el Entity Curso necesitamos los
	// examenes, pero en el Examen no necesitamos el curso. Si vemos la entity
	// Examen con la relación de Pregunta, veremos que en el método addPregunta(..)
	// hay una relación inversa, pero ese no es nuestro caso en este addExamen(..)
	public void addExamen(Examen examene) {
		this.examenes.add(examene);
	}

	public void removeExamen(Examen examen) {
		// Como estamos eliminando, es importante implementar el equals() en Examen
		this.examenes.remove(examen);
	}

	public List<CursoAlumno> getCursoAlumnos() {
		return cursoAlumnos;
	}

	public void setCursoAlumnos(List<CursoAlumno> cursoAlumnos) {
		this.cursoAlumnos = cursoAlumnos;
	}

	public void addCursoAlumno(CursoAlumno cursoAlumno) {
		this.cursoAlumnos.add(cursoAlumno);
	}

	public void removeCrusoAlumno(CursoAlumno cursoAlumno) {
		this.cursoAlumnos.remove(cursoAlumno);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Curso [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", createAt=");
		builder.append(createAt);
		builder.append("]");
		return builder.toString();
	}

}
