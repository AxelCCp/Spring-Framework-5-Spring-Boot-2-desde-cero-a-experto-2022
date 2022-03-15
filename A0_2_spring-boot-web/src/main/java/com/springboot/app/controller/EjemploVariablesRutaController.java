package com.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/variables")
public class EjemploVariablesRutaController {

	@GetMapping("/string/{texto}/{numero}")
	public String variables(@PathVariable(name="texto") String texto, @PathVariable(name="numero") Integer numero,Model model) {
		model.addAttribute("titulo", "recibir parámetros de la ruta @PathVariable");
		model.addAttribute("resultado", "texto enviado desde la ruta: " + texto + " Y el numero enviado en el Path es: " + numero);
		return "variables/ver";
	}
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("titulo", "Enviar parámetros de la ruta @PathVariable");
		return "variables/index";
	}
	
}
