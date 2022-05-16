package com.springboot.app;

//import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//ESTA CLASE ES PARA ALMACENAR LAS FOTOS EN UN DIRECTORIO FUERA DEL PROYECTO.

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/**
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		
		log.info(resourcePath);
		
		registry.addResourceHandler("/uploads/**")
		.addResourceLocations(resourcePath);
	}**/
	
	
	//CLASE199 :
	//SE PERSONALIZA LA PAGINA DE ERROR CUANDO UN USUARIO NO TIENE PERMISO PARA ENTRAR EN CIERTA PÁGINA DE LA APP.
	//ESTE ES UN CONTROLADOR PARAMETRIZABLE. UN VIEW CONTROLLER QUE ES PARA CARGAR UNA VISTA.
	//addViewControllers : EL MÉTODO SE TIENE QUE LLAMAR ASÍ.
	//CON REGISTRY SE REGISTRA EL EL VIEW CONTROLLER. ESTE CONTROLLER ES PARAMETRIZABLE O STATIC. NO TIENEN LA LOGICA DE CONTROLADOR.
	
	public void addViewControllers(ViewControllerRegistry registry) {
									//url mapping            //la vista
		registry.addViewController("/error_403").setViewName("/error_403");
	}
	
	

	private final Logger log = LoggerFactory.getLogger(getClass());
	
}
