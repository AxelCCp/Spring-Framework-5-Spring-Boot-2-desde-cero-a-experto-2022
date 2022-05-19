package com.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.Factura;

public interface IFacturaDao extends CrudRepository <Factura,Long>{

	
	//CLASE185 : METODO QUE OPTIMIZA LA APLICACION. ESTE MÉTODO OBTIENE LA FACTURA, PERO CON TODAS LAS RELACIONES HECHAS, PARA QUE NO HAGA TANTAS QUERYS.
	//YA QUE CUANDO UNO VA A VER EL DETALLE DE LA FACTURA EN  /FACTURA/VER/{ID},  AL CARGAR LOS DATOS EN LA PÁGINA, SE HACEN VARIAS QUERYS Y LA IDEA ES HACER SOLO UNA.
	
	
	//JOIN FETCH : SE REALIZAN LOS FETCH.
	
	@Query(	"select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1 ")
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
