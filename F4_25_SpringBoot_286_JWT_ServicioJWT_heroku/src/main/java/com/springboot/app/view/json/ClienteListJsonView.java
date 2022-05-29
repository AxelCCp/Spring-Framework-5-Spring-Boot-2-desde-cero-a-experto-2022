package com.springboot.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.springboot.app.models.entity.Cliente;

//1.-CON JSON ES MÁS SIMPLE Y NO SE NECESITA UNA CLASE WRAPPER. //SE EXTIENDE DE MappingJackson2JsonView Y SE SOBREESCRIBE EL METODO filterModel

//2.-ES PARA PODER FILTRAR O QUITAR ALGUNOS ELEMENTOS DEL MODEL QUE PASAMOS A LA VISTA.

//3.-SE PASA EL OBJETO MODEL AL MÉTODO filterModel()

//4.-HAY QUE FILTRAR AL CLIENTE.

@Component("listar.json")							
public class ClienteListJsonView extends MappingJackson2JsonView {  //1

	//2
	@Override
	protected Object filterModel(Map<String, Object> model) {
		model.remove("titulo");
		model.remove("page");
		
		//4
		Page<Cliente>clientes = (Page<Cliente>) model.get("clientes");
		model.remove("clientes");
		model.put("clientes",clientes.getContent());
		
		//3
		return super.filterModel(model);
	}

	
	
	
	
}
