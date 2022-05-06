package com.springboot.form.app.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.springboot.form.app.models.domain.Usuario;


//CLASE49//1
//CLASE50//2
//CLASE51//3
//CLASE55//4
//CLASE56//5

@Controller
public class FormController {

	//1
	@GetMapping("/form1")
	public String form1(Model model) {
		model.addAttribute("titulo","Formulario 1");
		return "form1";
	}
	
	@PostMapping("/form1")
	public String procesar1(Model model, 
			@RequestParam(name="username") String username,
			@RequestParam(name="password") String password,
			@RequestParam(name="email") String email) {
		model.addAttribute("titulo","Resultado del formulario 1");
		model.addAttribute("username",username);
		model.addAttribute("password",password);
		model.addAttribute("email",email);
		return "resultado1";
	}
	
	//2
	@GetMapping("/form2")
	public String form2(Model model) {
		model.addAttribute("titulo","Formulario 2");
		return "form2";
	}
	@PostMapping("/form2")
	public String procesar2(Model model, 
			@RequestParam(name="username") String username,
			@RequestParam(name="password") String password,
			@RequestParam(name="email") String email) {
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setEmail(email);
		usuario.setPassword(password);
		model.addAttribute("titulo","Resultado del formulario 2");
		model.addAttribute("usuario",usuario);
		return "resultado2";
	}
	
	//3
	@GetMapping("/form3")
	public String form3(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("titulo","Formulario 3");
		model.addAttribute("usuario",usuario);
		return "form3";
	}
	@PostMapping("/form3")
	public String procesar3(Model model, @Valid Usuario usuario, BindingResult result) {
		model.addAttribute("titulo","Resultado del formulario 3");
		if(result.hasErrors()) {
			Map<String,String>errores = new HashMap<String,String>();
			result.getFieldErrors().forEach(error -> {
				errores.put(error.getField(), "El campo ".concat(error.getField()).concat(" ").concat(error.getDefaultMessage()));
			});
			model.addAttribute("errores",errores);
			return "form3";
		}
		model.addAttribute("usuario",usuario);
		return "resultado3";
	}
	//4
	@GetMapping("/form4")
	public String form4(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("titulo","Formulario 4");
		model.addAttribute("usuario",usuario);
		return "form4";
	}
	@PostMapping("/form4")
	public String procesar4(Model model, @Valid Usuario usuario, BindingResult result) {
		model.addAttribute("titulo","Resultado del formulario 4");
		if(result.hasErrors()) {
			return "form4";
		}
		model.addAttribute("usuario",usuario);
		return "resultado4";
	}
	
	
}
