package com.springboot.webflux.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.springboot.webflux.models.dao.IProductoDao;
import com.springboot.webflux.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {

	@GetMapping("/listar")
	public String listar(Model model) {
		Flux<Producto>productos = productoDao.findAll().map(producto ->{
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		});
		
		
		productos.subscribe(prod -> log.info(prod.getNombre()));

		
		model.addAttribute("titulo","Lista de productos");
		model.addAttribute("productos",productos);
		return "listar";
	}
	
	
	@GetMapping("/listar-datadriver")
	public String listarDataDriver(Model model) {
		Flux<Producto>productos = productoDao.findAll().map(producto ->{
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).delayElements(Duration.ofSeconds(1));
		
		
		productos.subscribe(prod -> log.info(prod.getNombre()));

		
		model.addAttribute("titulo","Lista de productos");
		model.addAttribute("productos",new ReactiveDataDriverContextVariable(productos,2));
		return "listar";
	}
	
	
	@GetMapping("/listar-full")
	public String listarFull(Model model) {
		Flux<Producto>productos = productoDao.findAll().map(producto ->{
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).repeat(1000);
		
		
		model.addAttribute("titulo","Lista de productos");
		model.addAttribute("productos",productos);
		return "listar";
	}
	
	
	@GetMapping("/listar-chunked")
	public String listarChunked(Model model) {
		Flux<Producto>productos = productoDao.findAll().map(producto ->{
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).repeat(1000);
		
		
		model.addAttribute("titulo","Lista de productos");
		model.addAttribute("productos",productos);
		return "listar-chunked";
	}
	
	
	
	
	@Autowired
	private IProductoDao	productoDao;
	private static final Logger log = LoggerFactory.getLogger(ProductoController.class);
	
}
