package com.magadiflo.cursos.app.models.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Aquí el alumno_id no es llave, solo es para almacenar los ids de los alumnos

@Entity
@Table(name = "cursos_alumnos")
public class CursoAlumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "alumno_id", unique = true)
	private Long alumnoId;

	@JsonIgnoreProperties(value = { "cursoAlumnos" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curso_id")
	private Curso curso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAlumnoId() {
		return alumnoId;
	}

	public void setAlumnoId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CursoAlumno other = (CursoAlumno) obj;
		return Objects.equals(this.alumnoId, other.getAlumnoId());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CursoAlumno [id=");
		builder.append(id);
		builder.append(", alumnoId=");
		builder.append(alumnoId);
		builder.append(", curso=");
		builder.append(curso);
		builder.append("]");
		return builder.toString();
	}

}
