package com.springboot.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springboot.app.models.domain.ItemFactura;
import com.springboot.app.models.domain.Producto;

//import com.springboot.app.models.service.IService;
//import com.springboot.app.models.service.MiServicio1;

@Configuration
public class AppConfig {

	/*
	@Bean
	public IService registrarMiServicio() {
		return new MiServicio1();
	}
	*/
	
	
	@Bean("itemsfactura")
	public List<ItemFactura>registrarItems(){
		Producto producto1 = new Producto("camara sony",100);
		Producto producto2 = new Producto("bicicleta",200);
		ItemFactura linea1 = new ItemFactura(producto1,2);
		ItemFactura linea2 = new ItemFactura(producto2,4);
		return Arrays.asList(linea1,linea2);
	}
	@Bean("itemsfacturaOficina")
	public List<ItemFactura>registrarItemsOficina(){
		Producto producto1 = new Producto("monitor LG 24",250);
		Producto producto2 = new Producto("Notebook asus",500);
		Producto producto3 = new Producto("Impresora HP",50);
		Producto producto4 = new Producto("Escritorio",300);
		ItemFactura linea1 = new ItemFactura(producto1,2);
		ItemFactura linea2 = new ItemFactura(producto2,5);
		ItemFactura linea3 = new ItemFactura(producto3,3);
		ItemFactura linea4 = new ItemFactura(producto4,1);
		return Arrays.asList(linea1,linea2,linea3,linea4);
	}
	
}
