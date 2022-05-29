package com.springboot.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.springboot.app.models.entity.Cliente;

//DEF: ESTA ES LA CLASE WRAPPER QUE VA A CONTENER LA LISTA DE CLIENTES. 


//1.-ESTA CLASE VA A TENER UN LISTADO DE CLIENTES. ESTO SERÁ LO QUE SE VA A COVERTIR A XML.
//2.-LA CLASE DEBE TENER UN CONSTRUCTOR USANDO CAMPOS, CON LA LISTA DE CLIENTES. ASÍ TENEMOS UN CONSTRUCTOR QUE ASIGNA LA LISTA.
//3.-LA CLASE DEBE TENER UN CONSTRUCTOR VACÍO, PARA QUE LO PUEDA MANEJAR EL Jaxb2Marshaller. EL BEAN DE LA CLASE MvcConfig.
//4.-METODO GET.
//5.-INDICAMOS QUE ESTA EN LA CLASE ROOTXML
//6.-SE INDICA CUAL ES NUESTRO ATRIBUTO QUE VA A SER UN ELEMENTO XML POR CADA CLIENTE.
//7.- ***DESPUÉS SE AGREGA ESTA CLASE WRAPPER "ClienteList" AL METODO CON @BEAN DE LA LASE MvsConfig.java.

@XmlRootElement(name="clientes")//5
public class ClienteList {
	
	
	@XmlElement(name="cliente")//6
	public List<Cliente>clientes;//1

	//2
	public ClienteList(List<Cliente> clientes) {
		super();
		this.clientes = clientes;
	}

	//3
	public ClienteList() {
	}

	
	//4
	public List<Cliente> getClientes() {
		return clientes;
	}
	
	
	
	
	
	
	
	
}
