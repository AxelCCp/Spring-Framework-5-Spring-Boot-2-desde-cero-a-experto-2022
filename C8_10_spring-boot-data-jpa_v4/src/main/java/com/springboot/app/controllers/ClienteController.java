package com.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Cliente;
import com.springboot.app.models.service.IClienteService;
import com.springboot.app.util.paginator.PageRender;


//1.- @RequestParam: PARA OBTENER LA URL DE LA PÁGINA ACTUAL. SE USA EN ESTE CASO PARA USAR EL PAGINADOR.
	// int page: ES EL NOMBRE DEL PARÁMETRO.
//2.- SE CREA OBJ PAGEABLE Y SE DEFINEN 5 ELEMENTOS POR PÁGINA.
//3.- SE HACE LA INVOCACIÓN AL SERVICE, MÉTODO FINDALL() Y SE LE PASA EL pageRequest CON LA CANTIDAD DE CLIENTES A MOSTRAR POR PAGINA. POR LO TANTO "clientes" SE CONBIERTE EN UNA LISTA PAGINADA.

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	//1
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		
		//2
		Pageable pageRequest = PageRequest.of(page, 5);
		//3
		Page<Cliente>clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente>pageRender = new PageRender("/listar", clientes);
		
		model.addAttribute("titulo", "listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		
		return "listar";
	}
	
	@RequestMapping(value="/form")
	public String crear(Map<String,Object>model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "formulario de cliente");
		return "form";
	}
	
	
	@RequestMapping(value="/form",method=RequestMethod.POST)
	public String guerdar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo","Formulario de cliente");
			return "form";
		}
		
		
		if(!foto.isEmpty()) {
			Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			try {
				byte[]bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info","Ha subido correctamente la foto: " + foto.getOriginalFilename());
				//SE PASA EL NOMBRE DE LA FOTO AL CLEINTE
				cliente.setFoto(foto.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
		
		
		String mensajeFlash = (cliente.getId() != null)? "Cliente editado con éxito." : "Cliente agregado con éxito.";
		
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success",mensajeFlash);
		return "redirect:listar";
	}
	
	
	@RequestMapping(value="/form/{id}") 
	public String editar(@PathVariable Long id, Map<String,Object>model, RedirectAttributes flash) {
		Cliente cliente = null;
		
		if(id>0) {
			cliente = clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error","El id de cliente no existe en la bbdd!");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error","El id de cliente no puede ser 0!");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar cliente");
		return "form";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if(id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success","Cliente eliminado con éxito.");
		}
		
		return "redirect:/listar";
	}
	
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value="id")Long id, Map<String,Object>model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		if(cliente==null) {
			flash.addFlashAttribute("error","El cliente no existe en la BBDD");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente: " + cliente.getNombre());
		return "ver";
		
	}
	
	
	@Autowired
	private IClienteService clienteService; 	

}
