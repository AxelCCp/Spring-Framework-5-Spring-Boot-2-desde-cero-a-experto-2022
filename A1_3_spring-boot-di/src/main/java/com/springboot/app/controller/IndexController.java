package com.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.springboot.app.models.service.IService;


@Controller
public class IndexController {
	
	
	@GetMapping({"/","","/index"})
	public String index(Model model) {
		model.addAttribute("objeto", servicio.operacion());	
		return "index";
	}
	
	@Autowired
	private IService servicio;
	
}
