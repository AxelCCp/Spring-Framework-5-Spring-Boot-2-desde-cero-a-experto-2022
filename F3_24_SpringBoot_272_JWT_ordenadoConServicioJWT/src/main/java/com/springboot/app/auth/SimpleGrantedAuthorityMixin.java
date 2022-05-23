package com.springboot.app.auth;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityMixin {

	
	//CLASE 271 : 
	
	//LA EXPLICACIÓN DE LA EXISTENCIA DE ESTA CLASE ESTÁ AL FINAL DE LA CLASE 270.
	//ESTA CLASE ES NECESARIA PARA EL CÓDIGO DE LA CLASE JWTAuthorizationFilter, EN LA LÍNEA  Collection<? extends GrantedAuthority> authorities = Arrays.asList( new ObjectMapper().readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
	
	// HAY QUE CREAR UN CONSTRUCTOR QUE RECIBA UN PARAMETRO STRING ROLE DE SimpleGrantedAuthority, YA QUE AL PASAR LOS AUTHORITIES EN FORMATO JSON A UN OBJ SimpleGrantedAuthority, ES NECESARIO UN CONSTRUCTOR QUE RECIBA UN PARÁMETRO ROLE.
	
	//@JsonCreator : ESTA ES LA ANOTACIÓN QUE LE DICE A SPRING QUE ESTE ES EL CONSTRUCTOR PARA PASAR LOS AUTHORITIES DESDE JSON A OBJ JAVA.
	
	//("authority") : EL NOMBRE DEL ATRIBUTO QUE VIENE EN EL TOKEN, SE LLAMA AUTHORITY, POR LO TANTO HAY QUE USAR EL NOMBRE AUTHORITY Y NO ROLE, PARA PASARLO POR EL PARÁMETRO. PARA ESTO SE USA @JsonProperty("authority")
	
	@JsonCreator    
	public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) {
		
	}
	
}
