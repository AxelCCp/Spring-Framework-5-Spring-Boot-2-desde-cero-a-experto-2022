package com.springboot.webflux;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.springboot.webflux.models.dao.IProductoDao;
import com.springboot.webflux.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	
	//CLASE377: EN VEZ DE USAR UN APPLICACION.PROPERTIES, EN WEBFLUX SE AGREGA LA INFO AQUÍ
	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("productos").subscribe();
		
		Flux.just(
				new Producto("TV PANASONIC",234.23),
				new Producto("PC MSI",543.34),
				new Producto("PC LENOVO",654.45),
				new Producto("SILLA",234.12) 
				)
		.flatMap(producto -> {
			producto.setCreateAt(new Date());
			return	productoDao.save(producto);
		})
		
		.subscribe(producto -> log.info("Insert: " + producto.getId() + " " + producto.getNombre()));
		
	}

	
	@Autowired
	private IProductoDao productoDao; 
	
	private final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class); 
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
}
