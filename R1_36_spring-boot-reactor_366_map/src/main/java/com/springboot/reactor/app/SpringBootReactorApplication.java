package com.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

//CLASE363
//1.- SE IMPLEMENTA CommandLineRunner, PARA QUE SEA UNA APP DE CONSOLA O DE COMANDO.
//2.- SE CREA UN FLUX DE STRING NOMBRES Y CON JUST() SE CREA EL PRIMER OBSERVABLE.
//FLUX EX UN PUBLISHER, POR LO TANTO ES UN OBSERVABLE.
//3.-CON doOnNext() SE HACE ALGO CON EL FLUJO.
//4.-HAY QUE SUSCRIBIRSE AL FLUJO OBSERVABLE PARA QUE HAGA ALGO

//CLASE364 
//CUANDO SE SUSCRIBE, SE ESTÁ OBSERVANDO, POR LO TANTO ES UN COSUMIDOR QUE ESTÁ EJECUTANDO UN TIPO DE TAREA QUE ESTÁ EMITIENDO UN OBSERVABLE.

//--------FLUX NOMBRE----------------SUBSCRIBE
//-------- OBSERVABLE----------------OBSERVADOR

//EL OBSERVADOR PUEDE MANEJAR LA RESPUESTA DEL FLUX OBSERVABLE
//5.-SE AGREGÓ UN IF POR SI ES QUE HAY ERROR
//6.-EL OBSERVADOR MANEJA EL CASO DE ERROR.


//CLASE365
//6.-EL SUBSCRIBE PERMITE HACER UNA TAREA CUANDO TERMINA LA EJECUCIÓN DEL ÚLTIMO ELEMENTO DEL FLUX.
//SE USA AQUÍ LA INTERFAZ RUNNABLE, UN EVENTO QUE SE EJECUTA EN EL ONCOMPLETE.

//CLASE366
//7.-MAP

//CLASE367
//8.- SPLIT() : CONVIERTE UN STRING EN UN MATRIZ Y RECIVE UN PATRÓN PARA SEPARAR UNA CADENA.
//9.- FILTER() : SE APLICA FILTRO.

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//2																			//3
		Flux <Usuario> nombres = Flux.just("juan guzman","andres guzman","karen fulano","fran sultano","ximena mengano","bruce lee", "bruce wayne")
				
				//7
				.map(nombre -> {          		//8
					return new Usuario(nombre.split(" ")[0].toUpperCase() , nombre.split(" ")[1].toUpperCase());
					})
				.filter(usuario -> {
					return usuario.getNombre().toLowerCase().equals("bruce");
					})
				.doOnNext(usuario -> {
		
			if(usuario == null) {
				
				throw new RuntimeException("Nombres no pueden ser vacíos");
			}
			
			System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellido()));
		
				}) //7
			
				.map(usuario -> {
				String nombre = usuario.getNombre().toLowerCase();
				usuario.setNombre(nombre);
				return usuario;
				});
		
		//4									//5
		nombres.subscribe(e -> log.info(e.toString()), error -> log.error(error.getMessage()), new Runnable() {

			@Override
			public void run() {
				log.info("ha finalizado la ejecución del flux observable con éxito!");
				
			}
			
		});
	}
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

}
