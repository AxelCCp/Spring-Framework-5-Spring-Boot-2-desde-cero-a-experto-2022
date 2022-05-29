package com.springboot.app;

import java.util.Locale;

//import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//ESTA CLASE ES PARA ALMACENAR LAS FOTOS EN UN DIRECTORIO FUERA DEL PROYECTO.

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	
	//CLASE199 --------------------------------------------------------------------------------------------
	//SE PERSONALIZA LA PAGINA DE ERROR CUANDO UN USUARIO NO TIENE PERMISO PARA ENTRAR EN CIERTA PÁGINA DE LA APP.
	//ESTE ES UN CONTROLADOR PARAMETRIZABLE. UN VIEW CONTROLLER QUE ES PARA CARGAR UNA VISTA.
	//addViewControllers : EL MÉTODO SE TIENE QUE LLAMAR ASÍ.
	//CON REGISTRY SE REGISTRA EL EL VIEW CONTROLLER. ESTE CONTROLLER ES PARAMETRIZABLE O STATIC. NO TIENEN LA LOGICA DE CONTROLADOR.
	
	public void addViewControllers(ViewControllerRegistry registry) {
									//url mapping            //la vista
		registry.addViewController("/error_403").setViewName("/error_403");
	}
	//-----------------------------------------------------------------------------------------------------
	
	
	//CLASE207---------------------------------------------------------------------------------------------
	//SE TRASLADÓ ESTE CÓDIGO DESDE LA CLASE SpringSecurityConfig.java
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//-----------------------------------------------------------------------------------------------------
	

	//CLASE217---------------------------------------------------------------------------------------------
	//1
	//SE CREA MÉTODO localeResolver QUE GUARDA EL OBJ ""LOCALE" CON NUESTRA INTERNACIONALIZACION, CON CÓDIGO LOCALE "es" Y EL PAÍS "ES".
	//AL SER localeResolver UN OBJ DE SessionLocaleResolver, LA INFORMACIÓN SE VA A GUARDAR EL LA SESSIÓN HTTP, CADA VEZ QUE SE GUARDE UN NUEVO LOCALE.
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es","ES"));
		return localeResolver;
	}
	
	//2
	//SE CREA UN INTERCEPTOR. ESTE ES PARA CAMBIAR EL LOCALE CADA VEZ QUE SE ENVÍE EL PARÁMETRO DEL LENGUAJE CON EL NUEVO IDIOMA PARA CAMBIAR LOS TEXTOS DEL LENGUAJE.
	//SE ESPECIFICA EL NOMBRE DEL PARÁMETRO "lang" DE LENGUAJE. CADA VEZ QUE SE PASE POR URL A TRAVÉS DEL METODO GET EL PARÁMETRO "lang", POR EJ CON EL VALOR "Locale("es","ES")" SE VA A EJECUTAR EL INTERCEPTOR Y REALIZARÁ EL CAMBIO. 
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang");
		return localeInterceptor;
	}
	
	//3
	//METODO SOBREESCRITO DEL SOURCE/OVERRIDE
	//SE REGISTRA EL OBJ LocaleChangeInterceptor localeInterceptor EN LA APLICACION.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}	
	//-----------------------------------------------------------------------------------------------------
	
	
	//CLASE247 ............................................................................................
	//SE CONFIGURA EL BEAN QUE SE ENCARGA DE SERIALIZAR, DE CONVERTIR UN OBJ EN UN XML.
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		//SON LAS CLASES QUE SE VAN A CONVERTIR A XML. LAS CLASES RAIZ. LA CLASES PRINCIPAL QUE VA AA TENER ATRIBUTOS, DEPENDENCIAS. // setClassesToBeBound() RECIBE UN ARREGLO DE NUESTRAS CLASES.
		//***SINTESIS : LA CLASE ClienteList ES LA CLASE WRAPPER DONDE CONFIGURO LA WEA Y LA CHANTO EN ESTE MÉTODO CULIAO PA PASAR LA WEA A XML. 
		marshaller.setClassesToBeBound(new Class[] {com.springboot.app.view.xml.ClienteList.class});
		return marshaller;
	}
}
