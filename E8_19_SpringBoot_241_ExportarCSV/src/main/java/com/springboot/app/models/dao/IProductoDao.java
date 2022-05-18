package com.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto,Long>{

	// MÉTODO QUE DEBE REALIZAR UNA CONSULTA. PERO ESTA CONSULTA DEBE SER A ATRAVÉS DE UN PARÁMETRO. POR LO TANTO ES UNA CONSULTA WHERE LIKE
		//RECIBE UN PARÁMETRO QUE SERÍA EL TÉRMINO.
	
		//@Query ("SELECCIONA EL PRODUCTO CUANDO P DE PRODUCTO SEA IGUAL AL PARÁMETRO TERM.")
		//EL SELECT ES AL NIVEL DEL OBJETO ENTITY DE LA CLASE PRODUCTO. NO ES UN SELECT QUE SELECCIONA LA TABLA PRODUCTO.  
		//%?1% : EL DOBLE %% QUIERE DECIR BÚSQUEDA  Y SE HACE REFEERENCIA AL PARÁMETRO 1. EL PARÁMETRO TERM. 
	
	@Query("select p from Producto p where p.nombre like %?1% ")
	public List<Producto>findByNombre(String term);
	
	
	//ESTA ES OTRA MANERA DE HACER LA CONSULTA. ES UNA CONSULTA BASADA EN EL NOMBRE DEL MÉTODO. SPRING DATA HACE LA CONSULTA AL TENER UN ESTANDAR EN EL NOMBRADO DEL MÉTODO.
	public List<Producto>findByNombreLikeIgnoreCase(String term);
	
	
}

