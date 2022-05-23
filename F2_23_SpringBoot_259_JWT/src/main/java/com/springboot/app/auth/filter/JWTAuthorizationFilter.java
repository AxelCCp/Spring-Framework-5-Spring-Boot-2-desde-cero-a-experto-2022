package com.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.app.auth.SimpleGrantedAuthorityMixin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

//CLASE268

//ESTA CLASE HEREDA DE UNA CLASE PADRE QUE ES UN FILTRO DE SPRING SECURITY. BasicAuthenticationFilter. ESTA HEREDA A SU VEZ DE UNA CLASE QUE ENTRA EN FUNCIONAMIENTO CON CADA REQUEST.
//1.- SE USA EL CONSTRUCTOR QUE RECIBE POR PARAMETRO EL authenticationManager.
//2.-SE SOBRE ESCRIBE ESTE MÉTODO DE LA CLASE BasicAuthenticationFilter.
	//DENTRO SE VALIDA EL HEADER DEL AUTHORIZATION. SI EL HEADER ES DIFERENTE DE NULL, ENTONCES SE HACE LA VALIDACIÓN DEL TOKEN. Y AUTENTICARNOS PARA ACCEDER A LOS RECURSOS.
	//2.1.-CON ESTO, VALIDARÁ SOLO CUANDO LA CABECERA TRAIGA UN Authorization Y DEL TIPO Bearer.
//3.-MÉTODO HECHO MANUALMENTE.

//CLASE269.- 
//4.-SE REGISTRA EL FILTRO EN LA CLASE SpringSecurityConfig.
//5.-SE IMPLEMENTA LA VALIDACION DEL TOKEN. PARA ESTO SE INVOCA EL MÉTODO PARSE PARA ANALIZAR EL TOKEN A TRAVÉS DE LA MISMA CLASE QUE CREA EL TOKEN "JWTS".
	//5.1.-SE SETTEA LA CLAVE SECRETA, PARA ESTO SE USA LA LLAVE CON LA QUE SE GENERÓ EL TOKEN.
	//5.2.-AHORA SE VALIDA Y PARA ESTO SE TIENE QUE CONSEGUIR EL TOKEN DESDE LA CABECERA. AL CONSEGUIR EL TOKEN, SE LE TIENE QUE QUITAR EL "BEARER " PARA QUE SOLO QUEDE EL CÓDIGO. Y CON GETBODY() SE OBTIENEN LOS DATOS DEL TOKEN.
	//5.3.-SE PONE TODO EN TRY CATCH YA QUE parseClaimsJwt LANZA EXCEPTIONS. VARIAS HEREDAN DE JwtException, MIENTRAS QUE IllegalArgumentException NO HEREDA DE JwtException.
	//5.4.-VARIABLE BOOLEANA DE TOKEN VALIDO
	//5.5.-EL MÉTOODO GETBODY DEBE RETORNAR LOS CLAIMS. LOS DATOS QUE SE VA A USAR PARA LA AUTENTICACIÓN. SI NO HAY ERRORES, LOS VALORES DEL TOKEN SE ASIGNAN A LA VARIABLE Claims token.
//6.-SI ES VÁLIDO SE DA ACCESO AL RECURSO, SINO LA WEA SE DENIEGA.
	//6.1,. getSubject() : CONTIENE EL NOMBRE DEL USUARIO.
	//6.2.-SE OBTIENEN LOS ROLES. LA PLABRA "authorities" ES LA PALABRA CON LA CUAL SE GUARDÓ LA INFO EN LA CABECERA, EN LA CLASE JWT AUTHENTICATION FILTER
	//6.3.-SIEMPRE QUE UNO QUIERA ACCEDER A UN RECURSO HTTP, UNO SE TIENE QUE AUTENTICAR y PASAR LOS ROLES. PARA PASAR LOS ROLES SE HACE UNA COLLECTION GRANTEDAUTHORITY Y PARA CARGARLA SE PESCAN LOS ROLES Q VIENEN EN EL JSON Y SE PASAN A OBJ. EL TIPO DE LOS OBJETOS ES SIMPLE GRANTED AUTHORITIES Y CON [] 
				//SE PASA TODA LA WEA A ARRAY. 
	//6.4.-ESTA WEA SE ENCARGA DE MANEJAR EL CONTEXTO DE SEGURIDAD. ASIGNA EL OBJ authentication DENTRO DEL CONTEXTO. ESTA MIERDA AUTENTICA AL USUARIO DENTRO DEL REQUEST
	//6.5.-LUEGO SE SIGUE CON LA CADENA DE EJECUCION DEL REQUEST. HACIA LOS SERVLET, LOS OTRS FILTRO Y LOS CONTROLADORES.

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	//1
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	
	}

	//2
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//2.1
		String header = request.getHeader("Authorization");
		if(requiresAuthentication(header) == false) {
			chain.doFilter(request, response);
			return;
		}
		
		
		//5.4
		boolean validoToken;
		//5.5
		Claims token = null;
		
		//5.3
		try {
					
			//5
			token = Jwts.parserBuilder()
			//5.1
			.setSigningKey("algunaLlaveSecreta.1234567.algunaLlaveSecreta.1234567.algunaLlaveSecreta.1234567".getBytes())
			//5.2
			.build()
			.parseClaimsJws(header.replace("Bearer ", ""))
			.getBody();
			validoToken = true;
			
		}catch(JwtException | IllegalArgumentException e) {
			validoToken = false;
		}
		
		//6.3
		UsernamePasswordAuthenticationToken authentication = null;
		
		//6 ... SI EL TOKEN ES VALIDO SE DA ACCESO ...
		if(validoToken) {
			//6.1
			String username = token.getSubject();
			//6.2
			Object roles = token.get("authorities");
			//6.3																				 //addMixIn() : SE HACE REFERENCIA A LA CLASE MIXIN DONDE ESTA EL CONSTRUCTOR EXTERNO PARA PASAR LOS AUTHORITIES DE JSON A OBJ. 					
			Collection<? extends GrantedAuthority> authorities = Arrays.asList( new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class).readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
			authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
		}
		//6.4 
		SecurityContextHolder.getContext().setAuthentication(authentication);
		//6.5 
		chain.doFilter(request, response);
	}
	
	//3
	protected boolean requiresAuthentication(String header) {
		if(header == null || !header.startsWith("Bearer ")) {
			return false;
		}
		return true;
	}
	
	

}
