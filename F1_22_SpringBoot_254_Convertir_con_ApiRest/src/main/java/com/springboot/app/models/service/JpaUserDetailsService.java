package com.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.IUsuarioDao;
import com.springboot.app.models.entity.Role;
import com.springboot.app.models.entity.Usuario;

//SECCIÓN 15

//UserDetailsService : ESTA ES UNA CLASE PROPIA DE SPRING. NO NECESITA UNO HACER UNA CLASE SERVICE, YA VIENE LISTA.

//1.-SE OBTIENE EL USUARIO.
//2.-SE OBTIENEN LOS ROLES DEL USUARIO Y SE REGISTRAN EN UNA GrantedAuthority.
	// GrantedAuthority ES LA INTERFAZ, MIENTRAS QUE SimpleGrantedAuthority ES LA CLASE QUE LA IMPLEMENTA.
//3.-SE DEVUELVE UN OBJ DE TIPO USUARIO CON TODAS SUS WEAS.
//4.-SE LE PONE TRANSACTIONAL PQ BAJO LA MISMA TRANSACCIÓN SE HACE LA CONSULTA DEL USUARIO Y ADEMÁS SE OBTIENEN LOS ROLES.

//5.-VALIDACIONES Y LOGGER

//6.-SE MUESTRAN LOS NOMBRES DE LOS ROLES.


@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

	@Override
	@Transactional(readOnly=true) //4
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//1
		Usuario  usuario = usuarioDao.findByUsername(username);
		
		
		//5.1----------------------
		if(usuario == null) {
			logger.error("Error login: no existe el usuario: '" + username + "'");
			throw new UsernameNotFoundException("Username " + username + " no existe en el sistema!");
		}
		//-------------------------
		
		
		
		//2
		List<GrantedAuthority>authorities = new ArrayList<GrantedAuthority>();
		
		for(Role role : usuario.getRoles()) {
			logger.info("Role: ".concat(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		//5.2----------------------
		if(authorities.isEmpty()) {
			logger.error("Error login: usuario: '" + username + "' no tiene roles asignados.");
			throw new UsernameNotFoundException("Error login: usuario: '\" + username + \"' no tiene roles asignados.");
		}
		//-------------------------
		
		
		//3
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	//5
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
}
