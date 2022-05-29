package com.springboot.reactor.app.models;

public class Usuario {

	public Usuario(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
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
	
	
	
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellido=" + apellido + "]";
	}



	private String nombre;
	private String apellido;
	
}
