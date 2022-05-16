package com.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//CLASE192
//1.- EL AuthenticationManagerBuilder : ES PARA REGISTRAR A LO USUARIOS
//2.- x
//3.-ENCRIPTADOR. UN PASSWORD ENCODER.
//4.-SE OBTIENE LA INSTANCIA DEL BCryptPasswordEncoder Y SE GUARDA EN EL CONTENEDOR, YA QUE ESTÁ CON BEAN.
//5.-CON EL OBJ encoder SE PUEDEN REGISTRAR LOS USUARIOS Y SE PUEDE ENCRIPTAR LAS CONTRASEÑAS.
//SE CONSTRUYEN LOS USUARIOS Y SE PASA EL METODO passwordEncoder(). POR CADA USUARIO QUE SE REGISTRE SE PASA LA CONTRASEÑA Y SE ENCRIPTA.
//6.-SE CREAN LOS USUARIOS EN MEMORIA. SE PASAN USUARIO, CLAVE, ROLES.

//CLASE193
//7.-IMPLEMENTACION DE NUEVO MÉTODO PARA LAS AUTOTIZACIONES HTTP. PARA LAS RUTAS.

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	// 7
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// SE ASIGNAN LAS RUTAS DE ACCESO PÚBLICO.
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
				// RUTAS PRIVADAS
				.antMatchers("/ver/**").hasAnyRole("USER")
				.antMatchers("/uploads/**").hasAnyRole("USER")
				.antMatchers("/form/**").hasAnyRole("ADMIN")
				.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
				.antMatchers("/factura/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				//IMPLEMENTA FORMULARIO DE Login Y TAMBN EL LOG OUT PARA SALIR DE LA SESIÓN.  // SE PONE loginPage("/login") PARA IMPLEMENTAR NUESTRA PROPIA PAGINA DE INICIO, YA QUE SPRING YANOS DA UNA POR DEFECTO.
				.formLogin().loginPage("/login").permitAll()
				.and()
				.logout().permitAll()
				//CLASE199 :  SE AGREGA LA PÁGINA DE ERROR - ESTÁ RELACIONADO CON CLASE MVCCONFIG Y ERROR_403.HTML
				.and()
				.exceptionHandling().accessDeniedPage("/error_403");
	}

	
	
	// 3
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	
	@Autowired // 1
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		// 4
		PasswordEncoder encoder = passwordEncoder();
		// 5
		UserBuilder users = User.builder().passwordEncoder(password -> {
			return encoder.encode(password);
		});
		// 6
		builder.inMemoryAuthentication().withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
				.withUser(users.username("andres").password("12345").roles("USER"));
	}

}
