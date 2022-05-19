package com.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable {

	//CLASE158
	public Cliente() {
		facturas = new ArrayList<Factura>();
	}
	
	//------
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	//-----CLASE 158
	public List<Factura> getFacturas() {
		return facturas;
	}
	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public void addFactura(Factura factura) {
		facturas.add(factura);
	}
	
	

	@Override
	public String toString() {
		return nombre + " " + apellido;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String apellido;
	@NotEmpty
	@Email
	private String email;
	@NotNull
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") //CLASE 252 : SE ESTABLECE UN FORMATO PARA LA FECHA EN EL ARCHIVO JSON.
	private Date createAt;
	
	private String foto; 
	
	//cascade= CascadeType.ALL: QUIERE DECIR QUE LAS OPERACIONES COMO PERSIST Y DELETE SE VAN A EFECTUAR EN CADENA.
	//mappedBy : SE ASIGNA EL NOMBRE DEL ATRIBUTO DE LA CLASE CLIENTE QUE ESTÁ EN LA CLASE FACTURA. EN ESTE CASO "cliente".
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY, cascade= CascadeType.ALL) 
	//@JsonIgnore //CLASE 251 : ES PARA EVITAR EL BUBLE INFINITO AL CREAR EL ARCHIVO JSON. EL BUCLE CLIENTE --> FACTURA --> CLIENTE --FACTURA ---> ETC.  // EN LA CLASE 252 SE QUITA.
	@JsonManagedReference  // CLASE252: PARA QUE APAREZCAN LAS FACTURAS EN EL ARCHIVO JSON, SE USA ESTA ANOTACIÓN EN CONBINACIÓN CON @JSONBACKREFERENCE QUE VA EN EL ATRIBUTO CLIENTE DE LA CLASE FACTURA.  // @JsonManagedReference : SEÑALIZA QUE ESTA ES LA PARTE DELANTERA QUE SE SERIALIZA Y SE MUESTRA EN EL ARCHIVO JSON. // @JsonBackReference : MIESTRAS QUE ESTA ANOTACIÓN SEÑALIZA QUE ES LA PARTE POSTERIOR DE LA REALCIÓN Y QUE NO SE QUIERE MOSTRAR EN EL ARCHIVO JSON.
	private List<Factura>facturas;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
