package com.springboot.form.app.controllers;



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
	@Autowired
	private UsuarioValidador validador;
}
			