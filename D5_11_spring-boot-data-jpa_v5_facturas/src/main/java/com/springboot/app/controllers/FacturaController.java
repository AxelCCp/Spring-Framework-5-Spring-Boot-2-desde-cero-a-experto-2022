package com.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Cliente;
import com.springboot.app.models.entity.Factura;
import com.springboot.app.models.entity.ItemFactura;
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
	
	
	//HAY QUE OBTENER EL ID Y LA CANTIDAD DE LA LÍNEA DE CADA ITEM. PARA ESTO DE USA REQUESTPARAM, OBTENIENDOSE LOS DATOS DESDE LOS <INPUT> DEL ID Y LA CANTIDAD EN LA PLANTILLA plantilla-items.html.
	@PostMapping("/form")
	public String guardar(Factura factura, 
						@RequestParam(name="item_id[]", required=false) Long[]itemId,
						@RequestParam(name="cantidad[]", required=false) Integer[]cantidad,
						RedirectAttributes flash, SessionStatus status) {
		
		//POR CADA LÍNEA DE LA FACTURA SE OBTIENE EL PRODUCTO
		for(int i=0; i<itemId.length;i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);
			
			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}
		
		//SE GUARDA LA FACTURA EN LA BBDD
		clienteService.saveFactura(factura);
		//SE ELIMINA EL OBJ FACTURA DE LA SESIÓN 
		status.setComplete();
		//
		flash.addFlashAttribute("success", "Factura creada con éxito!");
		
		return "redirect:/ver/" + factura.getCliente().getId();
		
	}
	
	
	@Autowired
	private IClienteService clienteService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
}
