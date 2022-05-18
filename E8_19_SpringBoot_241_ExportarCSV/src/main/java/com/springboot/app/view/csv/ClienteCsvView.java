package com.springboot.app.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.springboot.app.models.entity.Cliente;

@Component("listar.csv")
public class ClienteCsvView extends AbstractView{

	//2.-DENTRO SE ASIGNA EL TIPO DE CONTENIDO
	public ClienteCsvView() {
		setContentType("text/csv");
	}


	//1.-MÉTODO DE LA CLASE ABSTRACTA
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//SE MODIFICA UN POCO LA RESPUESTA:
		//SE LE ASIGNA NOMBRE AL ARCHIVO.
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		//SE LE PASA EL CONTENT TYPE A LA RESPUESTA.
		response.setContentType(getContentType());
		//SE OBTIENE EL OBJ CLIENTES DEL MÉTODO LISTAR EN EL CONTROLADOR DE CLIENTES. PARA ESTO SE USA MODEL.
		//RECORDAR QUE ES UN  Page <Cliente> , YA QUE ESTÁ CON EL PAGINADOR 
		Page <Cliente> clientes = (Page<Cliente>) model.get("clientes");
		
		//SE CONVIERTE LA LISTA DE CLIENTES EN UN ARCHIVO PLANO.
		//response.getWriter() : OBTIENE EL ESCRITOR DE LA RESPUESTA. //AQUÍ SE GUARDA EL ARCHIVO DE LA RESPUESTA.
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		//SE CONSTRUYE UN ARREGLO QUE CONTENGA LOS NOMBRES DE LOS CAMPOS Y/O LOS ATRIBUTOS DE LA CLASE QUE SE VA A CONVERTIR EN ARCHIVO PLANO.
		//POR LO TANTO EL beanWriter VA A TOMAR UNA CLASE ENTITY, PESCA LOS ATRIBUTOS DE LA WEA Y LA PASA A ARCHIVO PLANO.
		
		//HEADER DEL ARCHIVO PLANO PARA CACHAR QUE ATRIBUTOS SE VAN A IMPRIMIR:
		String[]header = {"id", "nombre", "apellido", "email", "createAt"};
		
		//SE ESCRIBE UNA LÍNEA PARA EL HEADER
		beanWriter.writeHeader(header);
		
		//for PARA RECORRER LOS CLIENTES Y MOSTRAR LA WEA.
		for(Cliente cliente : clientes) {
			beanWriter.write(cliente, header);
		}
		//SE CIERRA EL RECURSO
		beanWriter.close();
		 
		 
	}
	
	
	//3.-METODO QUE SE SOBREESCRIBE MANUALMENTE. MÉTODO PARA ARCHIVO QUE SE DESCARGA. SE LE PONE TRUE, YA QUE GENERA UN CONTENIDO DESCARGABLE.
	@Override
	protected boolean generatesDownloadContent() {
		// TODO Auto-generated method stub
		return true;
	}

}
