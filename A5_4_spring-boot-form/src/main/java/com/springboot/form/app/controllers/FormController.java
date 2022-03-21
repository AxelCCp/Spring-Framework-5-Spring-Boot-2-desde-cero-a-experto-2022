package com.springboot.form.app.controllers;



import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.springboot.form.app.models.domain.Usuario;
import com.springboot.form.app.validation.UsuarioValidador;

@Controller
@SessionAttributes("usuario")
public class FormController {
	
	
	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("John");
		usuario.setApellido("Doe");
		usuario.setIdentificador("122.765.765-k");
		model.addAttribute("titulo","Formulario usuarios");
		model.addAttribute("usuario",usuario);
		return "form";
	}
	
	@PostMapping("/form") 
	public String procesar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status) {
		
		validador.validate(usuario, result);
		
		model.addAttribute("titulo","Resultado Formulario");
		
		if(result.hasErrors()) {
			/*Map<String,String>errores = new HashMap<>();
			result.getFieldErrors().forEach(e-> {
				errores.put(e.getField(), "El campo ".concat(e.getField()).concat(" ").concat(e.getDefaultMessage()));
			});
			model.addAttribute("error",errores);*/
			return "form";
		}
		
		model.addAttribute("usuario",usuario);
		status.setComplete();
		return "resultado";
	}
	
	
	@ModelAttribute("paises")
	public List<String>paises(){
		return Arrays.asList("España","Mexico","Colombia","EEUU","Australia");
	}
	
	@ModelAttribute("paisesMap")
	public Map<String,String>paisesMap(){
		Map<String,String>paises = new HashMap<String,String>();
		paises.put("ESP", "España");
		paises.put("MX", "Mexico");
		paises.put("COL", "Colombia");
		paises.put("USA", "EEUU");
		paises.put("AUS", "Australia");
		return paises;
	}
	
	
	
	
	
	
	@Autowired
	private UsuarioValidador validador;
}
			