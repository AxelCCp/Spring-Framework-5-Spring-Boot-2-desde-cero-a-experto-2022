package com.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

//CLASE 186. Optimizando consulta JPQL en Cliente con JOIN FETCH para obtener las facturas
//ESTO ES PARA QUE CUANDO SE HAGA UNA CONSULTA AL DETALLE DEL CLIENTE RETORNE DE MANERA AUTOMÁTICA Y SIN CARGA PEREZOSA LAS FACTURAS RELACIONADAS AL CLIENTE.
	
	//QUERY INICIAL
	//@Query("select c from Cliente c join fetch c.facturas f where c.id=?1")
	
	
	//SE LE HACE MODIFICACION, YA QUE LOS CLIENTES QUE NO TIENEN FACTURA APARECEN COMO QUE NO EXISTEN EN LA BASE DE DATOS, ESTO PASA POR EL INNER JOIN.
	//PARA QUE FUNCIONES CUANDO TENGA Y NO TENGA FACTURA, SE USA "LEFT".
	
	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	public Cliente fetchByIdWithFacturas(Long id);
		
	
	
}
