package com.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.app.models.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//CLASE 261

//1.-CLASE FILTRO QUE SE ENCARGA DE REALIZAR LA AUTENTICACIÓN. ESTE ES UN FILTRO INTERCEPTOR QUE SE EJECUTA EN EL REQUEST ANTES DE LLAMAR AL CONTROLADOR, VA A REALIZAR EL LOGIN.
   //ESTE FILTRO SE ADAPTA PARRA TRABAJAR CON JWT.
//2.-MÉTODO QUE REALIZA LA AUTENTICACIÓN Y TRABAJA POR DEBAJO CON JpaUserDetailsService
//3.-SE SACA ESTE CÓDIGO DESDE UsernamePasswordAuthenticationFilter.
//4.-SE CREA EL USERNAME PASSWORD AUTHENTICATION TOKEN. ESTE ES UN CONTENEDOR QUE CONTIENE LAS CREDENCIALES.
//5.-SE DELARA COMPONENTE AuthenticationManager QUE SE ENCARGA DE REALZIAR LA AUTHENTICATION USANDO EL JpaUserDetailsService.
//6.-SE CREA UN CONSTRUCTOR. ES EL ENCARGADO DE REALIZAR EL LOGIN.
//7.-AQUI SE RETORNA LA AUTENTICACIÓN. ESTA SE REALIZA CON EL TOKEN, CON EL USERNAME Y EL PASSWORD, A TRAVÉS DEL authenticationManager.
//8.-CODIGO DEL PROFE. EN ALGUNA PINCHE CLASE DE LA QUE SE HEREDA, ESTA EL OBJ LOGGER Y AQUÍ SE USA.
//9.-SE REGISTRA EL FILTRO EN LA CLASE SpringSecurityConfig, EN EL MÉTODO CONFIGURE.

//CLASE 264
//10.-A PARTIR DEL OBJ AUTHENTICATION, SE VA A OBTENER EL USERNAME Y TODOS LOS DATOS PARA CREAR EL JSON WEB TOKEN. 
//10.1.-TOKEN QUE SE LE RETORNA AL CLIENTE.
//10.2.-SE PASA EL TOKEN EL LA CABECERA DE LA RESPUESTA PARA EL USUARIO. CADA VEZ Q SE PASA EL TOKEN AL CLIENTE, SE HACE CON EL PREFIJO BEARER.
//10.3.-TAMBIÉN SE PASA EL TOKEN EN UN FORMATO JSON.


//CLASE266
//11.- SE HACE UN ELSE PARA MANEJAR LOS DATOS DEL USUARIO CUANDO ESTE LOS INGRESE POR EL RAW Y NO POR EL FORM DATA.
	//11.1.- SE CONVIERTE UN JSON A UN OBJ. SE ESPELCIFICA QUE SE QUIERE CONVERTIR A UN OBJ DE LA CLASE USUARIO.
	//11.2.- SE ASIGNA EL USUARIO y PASSWORD.

//CLASE267
//12.-SE IMPLEMENTA MÉTODO PARA CASO DE ERROR EN LA AUTHENTICACIÓN, GENERANDO UNA RESPUESTA.

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	//2
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		//3.-----------------------------------------
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		//if(username == null) {username = "";}
		//if(password == null) {password ="";}
		
			//8------
			if(username != null && password != null) {
				logger.info("Username desde request parameter (form-data): " + username);
				logger.info("Password desde request parameter (form-data): " + password);
			} else {
				//11----
				try {              //11.1
					Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
					//11.2----
					username = user.getUsername();
					password = user.getPassword();
					logger.info("Username desde request inputStream (raw): " + username);
					logger.info("Password desde request inputStream (raw): " + password);
					//----11.2
				} catch (StreamReadException e) {
					
					e.printStackTrace();
				} catch (DatabindException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}//----11
			}
			//-----.8
		
		username = username.trim();
		//-----------------------------------------.3
		
		//4
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
		
		//7
		return authenticationManager.authenticate(authToken);
	}

	
	//10
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
				
		
		//10.1--------------------------
		String username = ((User) authResult.getPrincipal()).getUsername();  //SE OBTIENE NOMBRE
		Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
		Claims claims = Jwts.claims(); //PARA OBTENER LOS CLAIMS
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));  //SE GUARDAN LOS ROLES. SE ESCRIBEN EN JSON.
		
		//SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		SecretKey secretKey = new SecretKeySpec("algunaLlaveSecreta.1234567.algunaLlaveSecreta.1234567.algunaLlaveSecreta.1234567".getBytes(), SignatureAlgorithm.HS256.getJcaName());
		
			String token = Jwts.builder()
					.setClaims(claims)
					.setSubject(username)
					//.signWith(SignatureAlgorithm.HS512, "Alguna.Clave.Secreta.123456.Alguna.Clave.Secreta.123456.Alguna.Clave.Secreta.123456".getBytes()) //(SE FIRMA EL TOKEN Y SE USA EL ALGORITMO HS512 , CLAVE SECRETA QUE DEBERÍA ESTAS CODIFICADA EN BASE 64).
					.signWith(secretKey)
					.setIssuedAt(new Date())                                      		     //FECHA DE CREACIÓN DEL TOKEN.
					.setExpiration(new Date(System.currentTimeMillis() + 14000000L))         //SE OBTIENEN LOS MILISEGUNDOS + LOS MILESEGUNDOS EN EL FUTURO. 4 HORAS EN MILISEGUNDOS.
					.compact();                                                  		     //SE COMPACTA EL TOKEN.
		
		//10.2
		response.addHeader("Authorization", "Bearer " + token);
		
		//10.3
		Map<String,Object>body = new HashMap<String, Object>();
		body.put("token",token);
		body.put("user", (User) authResult.getPrincipal());  //SE LE PASA EL USUARIO CONVERTIDO A UN USER DETAILS.
		body.put("mensaje", String.format("Hola %s, has iniciado sesión con éxito!", username));
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body)); //SE ESCRIBE EL JSON EN LA RESPUESTA. ObjectMapper : CONVIERTE UN OBJ DE JAVA EN UN JSON.
		response.setStatus(200);
		response.setContentType("application/json");
		//------------------------------
		
	}
	
	//12
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String,Object>body = new HashMap<String, Object>();
		body.put("mensaje", "Error de autenticación: username o password incorrectos.");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body)); //SE ESCRIBE EL JSON EN LA RESPUESTA. ObjectMapper : CONVIERTE UN OBJ DE JAVA EN UN JSON.
		response.setStatus(401); //CÓDIGO DE ERROR DE NO AUTORIZADO.
		response.setContentType("application/json");
	}


	//6
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		//SE QUITÓ EL SUPER
		this.authenticationManager = authenticationManager;
		//EL FILTRO SE VA A LLEVAR A CABO CUANDO LA RUTA SEA LA QUE SE INDICA AQUÍ Y SEA POST.
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));
	}
	

	//5
	private AuthenticationManager authenticationManager;
	
	
}
