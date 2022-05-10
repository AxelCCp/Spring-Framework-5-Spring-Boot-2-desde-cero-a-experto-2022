package com.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Cliente;
import com.springboot.app.models.entity.Factura;
import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.service.IClienteService;

@Controller
@RequestMapping("factura")
@SessionAttributes("factura")
public class FacturaController {

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value="clienteId") Long clienteId, Map<String,Object> model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(clienteId);
		
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la bbdd");
			return "redirect:/listar";
		}
		
		Factura factura = new Factura();
		factura.setCliente(cliente);
		
		model.put("factura", factura);
		model.put("titulo", "Crear factura");
		
		return "factura/form";
	}
	
	
	//ESTE ES EL MÉTODO QUE CARGA LOS PRODUCTOS EN LA WEA DE JQUERY.    //produces= {"application/json"}) : ESTA OTRA WEA HACE QUE EL MÉTODO ME DEVUELVA UN JSON.
	//@ResponseBody : SUPRIME CARGAR UNA VISTA DE THYMELEAF, PA' QUE ME TIRE LA WEA EN JSON Y EL JSON LO GUARDA EL EL BODY DE LA RESPONSE.
	@GetMapping(value="/cargar-productos/{term}", produces= {"application/json"})
	public @ResponseBody List<Producto>cargarProductos(@PathVariable String term){
		
		return clienteService.findByNombre(term);	
	}
	
	
	@Autowired
	private IClienteService clienteService;
}
