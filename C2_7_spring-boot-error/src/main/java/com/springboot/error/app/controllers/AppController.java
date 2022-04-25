package com.springboot.error.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.error.app.errors.UsuarioNoEncontradoException;
import com.springboot.error.app.models.domain.Usuario;
import com.springboot.error.app.services.IUsuarioService;

@Controller
public class AppController {
	
	@GetMapping("/index")
	public String index() {
		//Integer valor = 100/0;
		Integer valor = Integer.parseInt("10x");
		return "index";
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Integer id, Model model) {
		Usuario usuario = usuarioService.obtenerPorId(id);
		
		if(usuario==null) {
			throw new UsuarioNoEncontradoException(id.toString());
		}
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("titulo","Detalle usuario: ".concat(usuario.getNombre()));
		return "ver";
	}
	
	@Autowired
	private IUsuarioService usuarioService;
}
