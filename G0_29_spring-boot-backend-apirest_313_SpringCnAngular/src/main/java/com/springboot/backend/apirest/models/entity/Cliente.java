package com.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//CLASE 348 SE AGREGAN VALIDACIONES

@Entity
@Table(name="clientes")
public class Cliente implements Serializable{

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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	//METODO PARA ESTABLECER FECHA JUSTO ANTES DE CREAR A UN CLIENTE.
	@PrePersist   
	public void prePersist() {
		createAt = new Date();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	@NotEmpty(message = "No puede estar vacío")
	@Size(min=4,max=12, message = "El tamaño debe estar entre 4 y 12")
	@Column(nullable=false) //EL NOMBRE NO PUEDE SE NULO CLASE340
	private String nombre;
	@NotEmpty(message = "No puede estar vacío")
	private String apellido;
	@Email(message = "No es una dirección de correo bien formada")
	@NotEmpty(message = "No puede estar vacío")
	@Column(nullable=false, unique=true) //NO PUEDE SE NULO y DEBE SER UNICO CLASE340
	private String email;
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
