package com.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="facturas_items")
public class ItemFactura implements Serializable {

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio();
	}
	
	
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}





	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Integer cantidad;
	@ManyToOne(fetch = FetchType.LAZY)//HAY MUCHOS ITEMS, PERO CADA ITEM ES A UN SOLO TIPO DE PRODUCTO.  // CLASE 252 : SE CAMBIA DE LASY A EAGER. PARA QUE APAREZCAN BIEN LOS CLIENTES CON LAS FACTURAS Y EL DETALLE. DESPUÉS EL WEON VUELVE A CAMBIAR LA WEA A LASY PARA VER UNA MEJOR SOLUCIÓN.	
	@JoinColumn(name="producto_id")  //SE PONE DE MANERA EXPLICITA QUE SE VA A CREAR UN CAMPO LLAVE FORANEA producto_id EN LA TABLA ITEMFACTURA. 
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) //CLASE252 : REEMPLAZA LA WEA DEL LAZY O EL EAGER PARA LA WEA DE ARCHIVO JSON. ES PARA IGNORAR ATRIBUTOS EN EL JSON. LOS ATRIBUTOS VAN DENTRO DE LAS {}. 
	private Producto producto;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
}


