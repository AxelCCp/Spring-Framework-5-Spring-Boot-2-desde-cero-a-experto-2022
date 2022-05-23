package com.springboot.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.models.entity.Cliente;
import com.springboot.app.models.service.IClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {
	
	
	
	//CLASE255 : METODO API REST 
	//@ResponseBody : PARA RETURN EN REST.    //SE HABILITA EL MÉTODO EN CLASE SpringSecurityConfig.
		
	@GetMapping(value = "/listar")                        
	public  List<Cliente> listar() {
			
		return clienteService.findAll();
	
	}
	
		
	
		@Autowired
		private IClienteService clienteService; 	
	
}
