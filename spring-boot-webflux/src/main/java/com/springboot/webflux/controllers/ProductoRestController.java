package com.springboot.webflux.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.webflux.models.dao.IProductoDao;
import com.springboot.webflux.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

	
	@GetMapping
	public Flux<Producto>index(){
		Flux<Producto>productos = productoDao.findAll().map(producto ->{
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).doOnNext(prod -> log.info(prod.getNombre()));
		
		return productos;
	}
	
	
	@GetMapping("/{id}")
	public Mono<Producto>show(@PathVariable String id){
		
		//Mono<Producto>producto = productoDao.findById(id);
		Flux<Producto>productos = productoDao.findAll();
		Mono<Producto>producto = productos.filter(p -> p.getId().equals(id))
				.next()  //NEXT() EMITE EL 1ER ELEMENTO DEL FLUX EN UN NUEVO MONO.
				.doOnNext(prod -> log.info(prod.getNombre()));
		return producto;
	}
	
	
	
	@Autowired
	private IProductoDao	productoDao;
	private static final Logger log = LoggerFactory.getLogger(ProductoController.class);
	
}
