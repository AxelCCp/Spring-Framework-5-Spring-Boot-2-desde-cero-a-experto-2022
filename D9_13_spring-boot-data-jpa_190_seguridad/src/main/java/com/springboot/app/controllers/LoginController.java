package com.springboot.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	//1.- PRINCIPAL :  PERMITE VALIDAR, SI PRINCIPAL ES != NULL ES PQ YA SE INICIÓ SESIÓN ANTERIORMENTE. SE USA PARA QUE NO NOS VUELVA A MANDAR AL LOGIN DESPUÉS DE YA HABER INICIADO LA SESIÓN.
	//2.- @RequestParam : CUANDO HAY ERROR EN EL INICIO DE SESION, SPRING NOS MANDA UNA BANDERA DE "ERROR" EN LA URL Y CON ESTA ANOTACIÓN LO ATAJAMOS, PARA ENVIAR UN MENSAJE DE ERROR DE INICIO DE SESION AL USUARIO. 
	//3.- SPRING NOS MANDA UNA BANDERA DE LOGOUT, SE ATAJA Y SE PONE UN MENSAJE DE CIERRE DE SESIÓN.
	
	@GetMapping("/login")             //1
	public String login(Model model, Principal principal, RedirectAttributes flash,
						@RequestParam(value="error", required=false) String error,
						@RequestParam(value="logout", required=false) String logout) {
		
		if(principal != null) {
			flash.addFlashAttribute("info", "Ya ha iniciado sesión anteriormente.");
			//REDIRIGE A LA PAG DE INICIO PARA QUE NO HAGA DOBLEMENTE INICIO DE SESSION.
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Error en el inicio de sesión. Usuario y/o contraseña incorrectos.");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
		
		return "login";
	}
	
}
