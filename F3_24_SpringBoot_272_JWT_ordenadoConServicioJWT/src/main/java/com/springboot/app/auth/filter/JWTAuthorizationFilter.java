package com.springboot.app.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.springboot.app.auth.service.JWTService;
import com.springboot.app.auth.service.JWTServiceImpl;


//SECCIÓN 23 / ÚLTIMAS CLASES : PARA ENTENDER TODA ESTA WEA, PRIMERO ENTIENDE EL CODIGO DEL PROYECTO F2_23. YA QUE ESTE ES LA MISMA WEA PERO OPTIMIZADA.


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header = request.getHeader(JWTServiceImpl.HEADER_STRING);
		if(requiresAuthentication(header) == false) {
			chain.doFilter(request, response);
			return;
		}
		
		
		UsernamePasswordAuthenticationToken authentication = null;
		
		//... SI EL TOKEN ES VALIDO SE DA ACCESO ...
		if(jwtService.validate(header)) {
			authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
	
		chain.doFilter(request, response);
	}
	

	protected boolean requiresAuthentication(String header) {
		if(header == null || !header.startsWith(JWTServiceImpl.TOKEN_PREFIX)) {
			return false;
		}
		return true;
	}
	
	
	private JWTService jwtService;
	
}
