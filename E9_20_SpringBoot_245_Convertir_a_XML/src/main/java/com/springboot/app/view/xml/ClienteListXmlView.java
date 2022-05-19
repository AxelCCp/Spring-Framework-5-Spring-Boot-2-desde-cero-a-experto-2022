package com.springboot.app.view.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.springboot.app.models.entity.Cliente;

//ESTA ES LA CLASE QUE FORMA A VISTA DEL XML

//1.-SE SOBREESCRIBE MÉTODO MANUALMENTE
//2.-
	//EL MÉTODO LISTAR DEL CONTROLLER DE CLIENTES DEVUELVE MÁS DE UN MODEL. DEVUELVE : "titulo", "clientes" y "page". 
	//Y SOLO SE QUIRE EL MODEL DE CLIENTES. PARA ESTO HAY QUE REMOVER.
//3.-LUEGO SE ALMACENAN LOS CLIENTES DEL model.addAttribute("clientes", clientes); DEL CONTROLLER DE CLIENTES.
//4.-UNA VEZ ALMACENADA LA INFO DEL OS CLIENTES, SE BORRA EL MODEL DE CLIENTES. CON ESTO EL MODEL QUEDA LIMPIO.
//5.-
	//AHORA SE GUARDA EN EL MODEL EL CLIENTE LIST JUNTO CON EL LISTADO DE CLIENTES.
	//SE LE PASA EL CLIENTELIST A LA CLASE WRAPPER POR EL CONSTRUCTOR.
@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView{

	//1
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//2
		model.remove("titulo");
		model.remove("page");
		
		//3
		Page<Cliente>clientes=(Page<Cliente>) model.get("clientes");
		
		//4 
		model.remove("clientes");
		
		//5
		model.put("clienteList", new ClienteList(clientes.getContent()));
		
		//VIENE CON EL METODO ESTO
		super.renderMergedOutputModel(model, request, response);
	}
	
	
	//2.-CONSTRUCTOR QUE RECIBE UN OBJ MARSHALLER. ESTE CONSTRUCTOR SE SACÓ DE SOURCE/GENERATE CONSTRUCTOR FROM SUPER CLASS. Y SE LE CAMBÍO EL MARSHALLER POR EL Jaxb2Marshaller.
		//Marshaller : ES LA INTERFAZ. //MIENTRAS QUE Jaxb2Marshaller ES LA IMPLEMENTACIÓN QUE HACE REFERENCIA AL BEAN DE MvcConfig.
	@Autowired
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);	
	}
	
	
	
	

	
}
