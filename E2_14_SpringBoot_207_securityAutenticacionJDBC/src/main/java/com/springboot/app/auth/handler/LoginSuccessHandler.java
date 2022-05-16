package com.springboot.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

//CLASE201 : ESTA CLASE ES PARA ENTREGAR UN MENSAJE DE ÉXITO DEL INICIO DE SESIÓN.

//1.-SE HEREDA DE SimpleUrlAuthenticationSuccessHandler.
//2.-SE OBTIENE E SESSION MAP FLASH MANAGER PARA PODER REGISTRAR UN FLASHMAP. UN ARREGLO ASOCIATIVO DE JAVA QUE CONTENGA LOS MENSAJES FLASH.
//3.-SE REGISTRA EL FLASHMAP EN EL MANAGER.
//4.-LA REGISTRAMOS COMO UN COMPONENTE BEAN DE SPRING.
//5.-DESPUÉS HAY QUE IR A LA CONFIGURACIÓN DE LA CLASE SPRINGSECURITYCONFIG Y AHI SE INYECTA. ...


//CLASE202 : 
//6.- SE OBTIENE EL NOMBRE DEL USUARIO A TRAVÉS DEL OBJ AUTHENTICATION Y EN EL SALUDO DEL FLASH SE AGREGA EL NOMBRE DEL USUARIO CON CONCATENACIÓN.
//7.- SE VERIFICA POR SI CASO QUE NO SEA NULL Y CON UN LOG SE MUESTRA LA INFO EN CONSOLA. 
	//EL OBJ LOGGER SE PUEDE USAR ASÍ NOMÁS, YA QUE SimpleUrlAuthenticationSuccessHandler HEREDA DE UNA CLASE ABSTRACTA Y ESTA CLASE TIENE EL LOGGER.
	//*** EN EL MÉTODO LISTAR DEL CLIENTE CONTROLLER TAMBIÉN ESTÁ IMPLEMENTADO ESTE MÉTODO, Y SE PASA LA INFO POR LA CONSOLA.
	//*** TAMBN SE PUEDE OBTENER EL USER DE MANERA ESTATICA EN CUALQUIER PARTE DE PROGRAMA. ESTO ESTÁ EXPLICADO EN EL CONTROLADOR CLIENTE MET LISTAR.

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//2
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		FlashMap flashMap = new FlashMap();        //6
		flashMap.put("success", "Hola! " + authentication.getName() +  " Ha iniciado sesión con éxito!");
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		//7
		if(authentication != null) {
			logger.info("El usuario '" + authentication.getName() + "' ha iniciado sesión con éxito");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
	
	
}
