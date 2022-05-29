package com.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.springboot.app.auth.filter.JWTAuthenticationFilter;
import com.springboot.app.auth.filter.JWTAuthorizationFilter;
import com.springboot.app.auth.service.JWTService;
import com.springboot.app.models.service.JpaUserDetailsService;

//SECCIÓN 23 / ÚLTIMAS CLASES : PARA ENTENDER TODA ESTA WEA, PRIMERO ENTIENDE EL CODIGO DEL PROYECTO F2_23. YA QUE ESTE ES LA MISMA WEA PERO OPTIMIZADA.

					    	//PARA @Secured       //PARA @PreAuthorize
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true) //CLASE205 : SE HABILITAN ANOTACIONES DE SEGURIDAD   // @Secured y @PreAuthorize SON LA MISMA WEA.
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// SE ASIGNAN LAS RUTAS DE ACCESO PÚBLICO.
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar", "/locale", "/listar-rest").permitAll()
		
				.anyRequest().authenticated()
				
				//CLASE259 : APLICANDO SECURIDAD CON JWT - SE QUITA SEGURIDAD CON SESIONES. //SE DESHABILITA LA PROTECCIÓN CsRF. SE DESHABILITA PQ SE VA A USAR JASON WEB TOKEN, EN VEZ DEL TOKEN DE PROTECCIÓN CONTRA CSRF.
				.and()
					//CLASE 261 : SE REGISTRA FILTRO. EN EL PARÁMETRO SE LE PASA EL AUTHENTICATION MANAGER
					.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
					//CLASE269 : SE AGERGA EL JWTAuthorizationFilter.
					.addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtService))   
					
				//259
				.csrf().disable()
				
				//CLASE259 :CONFIGURACIÓN DE SPRING SECURITY, SE HABILITA EN EL SESSION MANAGER. // SessionCreationPolicy.STATELESS : DESHABILITA EL USO DE SESSIONES.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	
	@Autowired 
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		//CLASE214-------CONFIGURACIÓN Y REGISTRO DEL JpaUserDetailsService---------------------------------------------
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		//---------------------------------------------------------------------------------------------------------------
		
	}

	
	
	//CLASE207
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//CLASE214 --SE INYECTA PARA LA CONEXIÓN CON LA BASE DE DATOS.
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	//CLASE273
	@Autowired
	private JWTService jwtService;

	
}
