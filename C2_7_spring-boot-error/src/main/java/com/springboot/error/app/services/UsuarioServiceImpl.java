package com.springboot.error.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.error.app.models.domain.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	public UsuarioServiceImpl() {
		this.lista = new ArrayList<>();
		this.lista.add(new Usuario(1,"vgyvhubhu","kñlkñkñl"));
		this.lista.add(new Usuario(2,"skhfs","jkhjuyi"));
		this.lista.add(new Usuario(3,"vbnvnbv","rtcytty"));
		this.lista.add(new Usuario(4,"uiy","lkj"));
		this.lista.add(new Usuario(5,"uiy","lksdfsj"));
		this.lista.add(new Usuario(6,"uiaerwy","lkwerj"));
		this.lista.add(new Usuario(7,"uiwery","ldfgdfkj"));
	}
	
	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return this.lista;
	}

	@Override
	public Usuario obtenerPorId(Integer id) {
		Usuario resultado = null;
		for(Usuario u : this.lista) {
			if(u.getId().equals(id)) {
				resultado = u;
				break;
			}
		}
			
		return resultado;
	}
	private List<Usuario>lista;
}
