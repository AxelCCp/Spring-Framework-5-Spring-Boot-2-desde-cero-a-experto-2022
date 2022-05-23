package com.springboot.app.auth.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.app.auth.SimpleGrantedAuthorityMixin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//SECCIÓN 23 / ÚLTIMAS CLASES : PARA ENTENDER TODA ESTA WEA, PRIMERO ENTIENDE EL CODIGO DEL PROYECTO F2_23. YA QUE ESTE ES LA MISMA WEA PERO OPTIMIZADA.


@Component
public class JWTServiceImpl implements JWTService {

	@Override
	public String create(Authentication auth) throws IOException {
		
		String username = ((User) auth.getPrincipal()).getUsername(); 
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		Claims claims = Jwts.claims(); // PARA OBTENER LOS CLAIMS
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles)); 
																				
		SecretKey secretKey = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());

		String token = Jwts.builder()
				.setClaims(claims).setSubject(username)
				.signWith(secretKey).setIssuedAt(new Date())                           // FECHA DE CREACIÓN DEL TOKEN.
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)) // SE OBTIENEN LOS MILISEGUNDOS + LOS															
				.compact(); 														   // SE COMPACTA EL TOKEN.
		return token;
	}

	@Override
	public boolean validate(String token) {
		try {
			getClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public Claims getClaims(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(SECRET.getBytes())
				.build().parseClaimsJws(resolve(token)).getBody();
		return claims;
	}

	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get("authorities");
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
						.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

		return authorities;
	}

	@Override
	public String resolve(String token) {
		// TODO Auto-generated method stub
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			return token.replace(TOKEN_PREFIX, "");
		}
		return null;
	}
	
	//SE CODIFICA EN BASE64
	public static final String SECRET = Base64Utils.encodeToString("algunaLlaveSecreta.1234567.algunaLlaveSecreta.1234567.algunaLlaveSecreta.1234567".getBytes());
	public static final long EXPIRATION_DATE = 14000000L; // 4HORAS EN MILISEGUNDOS.
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	
}
