package com.springboot.form.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.form.app.models.domain.Role;

@Service
public class RoleServiceImpl implements IRoleService {

	public RoleServiceImpl() {
		this.roles = new ArrayList<>();
		this.roles.add(new Role(1,"Administrador","ROLE_ADMIN"));
		this.roles.add(new Role(2,"Moderador","ROLE_MODERATOR"));
		this.roles.add(new Role(3,"usuario","ROLE_USER"));
	}
	
	@Override
	public List<Role> listar() {
		return roles;
	}

	@Override
	public Role obtenerPorId(Integer id) {
		Role resultado = null;
		for(Role role : roles) {
			if(id==role.getId()) {
				resultado = role;
				break;
			}
		}
		return resultado;
	}

	private List<Role>roles;
}
