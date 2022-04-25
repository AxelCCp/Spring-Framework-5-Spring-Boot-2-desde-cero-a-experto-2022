package com.springboot.error.app.errors;

public class UsuarioNoEncontradoException extends RuntimeException {
	
	public UsuarioNoEncontradoException(String id) {
		super("Usuario con id: ".concat(id).concat(" no existe en el sistema."));
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}
