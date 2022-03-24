package com.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.form.app.models.domain.Pais;

@Service
public class PaisServiceImpl implements PaisService {

	private List<Pais>lista;
	
	public PaisServiceImpl() {
		this.lista = Arrays.asList(
				new Pais(1,"ES","EEUU"),
				new Pais(2,"MX","MÉXICO"),
				new Pais(3,"CA","CANADA"),
				new Pais(4,"FR","FRANCIA"),
				new Pais(5,"COL","COLOMBIA"));
	}
	
	@Override
	public List<Pais> listar() {
		return lista;
	}

	@Override
	public Pais obtenerPorId(Integer id) {
		Pais resultado = null;
		for(Pais pais : this.lista) {
			if(id == pais.getId()) {
				resultado=pais;
				break;
			}
		}
		return resultado;
	}
}
