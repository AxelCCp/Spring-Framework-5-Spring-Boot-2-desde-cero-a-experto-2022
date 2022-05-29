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
import com.springboot.app.auth.service.JWTService;
import com.springboot.app.auth.service.JWTServiceImpl;
import com.springboot.app.models.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//SECCIÓN 23 / ÚLTIMAS CLASES : PARA ENTENDER TODA ESTA WEA, PRIMERO ENTIENDE EL CODIGO DEL PROYECTO F2_23. YA QUE ESTE ES LA MISMA WEA PERO OPTIMIZADA.

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
			
			if(username != null && password != null) {
				logger.info("Username desde request parameter (form-data): " + username);
				logger.info("Password desde request parameter (form-data): " + password);
			} else {
				
				try {             
					Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
					username = user.getUsername();
					password = user.getPassword();
					logger.info("Username desde request inputStream (raw): " + username);
					logger.info("Password desde request inputStream (raw): " + password);
					
				} catch (StreamReadException e) {
					e.printStackTrace();
				} catch (DatabindException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		username = username.trim();
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
		return authenticationManager.authenticate(authToken);
	}

	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
				
		String token = jwtService.create(authResult);
		response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);
	
		Map<String,Object>body = new HashMap<String, Object>();
		body.put("token",token);
		body.put("user", (User) authResult.getPrincipal());  //SE LE PASA EL USUARIO CONVERTIDO A UN USER DETAILS.
		body.put("mensaje", String.format("Hola %s, has iniciado sesión con éxito!", authResult.getName()));
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body)); //SE ESCRIBE EL JSON EN LA RESPUESTA. ObjectMapper : CONVIERTE UN OBJ DE JAVA EN UN JSON.
		response.setStatus(200);
		response.setContentType("application/json");
	}
	

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

                                                                           
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		//SE QUITÓ EL SUPER
		this.authenticationManager = authenticationManager;
		//EL FILTRO SE VA A LLEVAR A CABO CUANDO LA RUTA SEA LA QUE SE INDICA AQUÍ Y SEA POST.
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));
		//13.3
		this.jwtService = jwtService;
	}
	

	private AuthenticationManager authenticationManager;
	private JWTService jwtService;
	
}
