package com.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {

	
	//ESTE MÉTODO SE USA CUANDO UNO ESTÁ EN EL LISTADO DE CLIENTES, Y NO SE DEVUELVA A LA PAGINA 1 DEL LISTADO, CUANDO UNO CAMBIA EL IDIOMA DE LA PÁGINA. 
	
	//DESPUÉS DE IMPLEMENTAR ESTE MÉTODO, SE HACEN ALGUNAS MODIDICACIONES EN EL LAYOUT.HTML.
	
	//TAMBIEN SE AGREGA EL METODO LOCALE AL PERMITALL() DE LA CLASE SpringSecurityConfig, PARA QUE NO REDIRIJA A LA PÁGINA DE LOGIN
	//CADA VEZ QUE SE QUIERA CAMBIAR EL IDIOMA.
	
	@GetMapping("/locale")
	public String locale(HttpServletRequest request) {
		//SE PASA EL NOMBE DEL PARÁMETRO QUE SE QUIERE OBTENER DE LA CABECERA DEL REQUEST.
		//EL PARÁMETRO REFERER ENTREGA LA REFERENCIA (EL link) DE LA ÚLTIMAA URL.
		String ultimaUrl = request.getHeader("referer");
		return "redirect:".concat(ultimaUrl);
	}
	
}
