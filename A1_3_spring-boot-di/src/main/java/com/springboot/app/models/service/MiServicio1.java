package com.springboot.app.models.service;

import org.springframework.stereotype.Service;

@Service("service1")
public class MiServicio1 implements IService {
	
	public String operacion() {
		return "ejecutando algún proceso importante...";
	}
}
