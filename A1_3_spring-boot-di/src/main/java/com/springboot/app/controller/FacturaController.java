package com.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.app.models.domain.Factura;

@Controller
@RequestMapping("factura")
public class FacturaController {
	
	@GetMapping("/ver")
	public String ver(Model model) {
		model.addAttribute("factura",factura);
		model.addAttribute("titulo","Ejemplo fatura con inyección de dependencia");
		return "factura/ver"; 
	}
	
	@Autowired
	private Factura factura;

}