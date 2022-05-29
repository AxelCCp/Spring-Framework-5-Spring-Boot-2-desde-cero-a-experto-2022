package com.springboot.app.auth.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

//SECCIÓN 23 / ÚLTIMAS CLASES : PARA ENTENDER TODA ESTA WEA, PRIMERO ENTIENDE EL CODIGO DEL PROYECTO F2_23. YA QUE ESTE ES LA MISMA WEA PERO OPTIMIZADA.

//CLASE272 : EN ESTA SE LISTAN LOS MÉTODOS QUE DEBE TENER CUALQUIER IMPLEMENTACIÓN PARA USAR JWT. 


public interface JWTService {
	
	//SE ENCARGA DE CREAR EL TOKEN.
	public String create(Authentication auth) throws IOException;
	
	//VALIDA EL TOKEN. RECIBE EL TOKEN POR PARÁMETRO
	public boolean validate(String token);
	
	//MÉTODO PARA OBTENER LOS CLAIMS
	public Claims getClaims(String token);
	
	//MÉTODO PARA OBTENER LOS USERNAME Y LOS ROLES
	public String getUsername(String token);
	
	//MÉTODO PARA OBTENER LOS ROLES.
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;
	
	//METODO QUE SEPARA EL "BEARER " DEL CÓDIGO DEL TOKEN
	public String resolve(String token);
}
