package com.springboot.form.app.models.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Usuario {
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}


	@NotEmpty
	@Size(min=3,max=8)
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	@Email(message="Correo con mensaje incorrecto.")
	private String email;
	@NotEmpty(message="El nombre no puede ser vacío.")
	private String nombre;
	@NotEmpty
	private String apellido;
	@Pattern(regexp = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[a-zA-Z]{1}")
	private String identificador;
}
