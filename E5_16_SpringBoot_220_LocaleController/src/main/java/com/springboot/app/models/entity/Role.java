package com.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//CLASE 211 : 
//uniqueConstraints : SE CREA UN ÍNDICE QUE INDICA QUE EL "authority" Y  LA LLAVE FORANEA "user_id", SON ÚNICOS.

@Entity
@Table(name="authorities",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"user_id","authority"})
		}
)          
public class Role implements Serializable{

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String authority;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
