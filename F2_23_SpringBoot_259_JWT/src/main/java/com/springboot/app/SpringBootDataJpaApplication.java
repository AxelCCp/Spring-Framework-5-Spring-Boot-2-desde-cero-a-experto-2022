package com.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//CLASE207: 
//SE IMPLEMENTA EL MÉTODO RUN.
//1.-AQUÍ SE VAN A GENERAR 2 CONTRASEÑAS ENCRIPTADAS. ... TRABAJANDO CON JDBC.


@SpringBootApplication
public class SpringBootDataJpaApplication /*implements CommandLineRunner*/{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	/*
	@Override
	public void run(String... args) throws Exception {
		//1
		String password = "12345";
		for(int i=0; i<2; i++) {
			String bCryptPassword = passwordEncoder.encode(password);
			System.out.println(bCryptPassword);
		}
		
	}
	

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	*/
}
