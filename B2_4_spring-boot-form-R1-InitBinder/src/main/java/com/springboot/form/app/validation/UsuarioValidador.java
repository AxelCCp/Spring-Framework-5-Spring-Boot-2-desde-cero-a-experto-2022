package com.springboot.form.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.springboot.form.app.models.domain.Usuario;

@Component
public class UsuarioValidador implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//Usuario usuario = (Usuario) target; //SE COMENTA EN CLASE 64.
		
		
		//FORMA1
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "requerido.usuario.nombre");
		//FORMA2
		/*
		if(usuario.getNombre().isEmpty()) {
			errors.rejectValue("nombre", "NotEmpty.usuario.nombre");
		}*/
		
		
		//CLASE 64: SE COMENTA, YA QUE SE PONE UNA ANOTACIÓN PERSONALIZADA.
		/*
		if(usuario.getIdentificador().matches("[0-9]{3}.[0-9]{3}.[0-9]{3}-[a-zA-Z]{1}") == false) {
			errors.rejectValue("identificador","pattern.usuario.identificador");
		}*/
	}

}
