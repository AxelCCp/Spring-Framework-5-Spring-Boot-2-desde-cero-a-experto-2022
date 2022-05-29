package com.springboot.webflux.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springboot.webflux.models.documents.Producto;

public interface IProductoDao extends ReactiveMongoRepository<Producto, String>{

	
	
}
