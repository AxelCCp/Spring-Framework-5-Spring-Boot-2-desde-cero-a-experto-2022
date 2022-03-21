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
		// TODO Auto-generated method stub
		Usuario usuario = (Usuario) target;
		//FORMA1
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "requerido.usuario.nombre");
		//FORMA2
		/*
		if(usuario.getNombre().isEmpty()) {
			errors.rejectValue("nombre", "NotEmpty.usuario.nombre");
		}*/
		
		if(usuario.getIdentificador().matches("[0-9]{3}.[0-9]{3}.[0-9]{3}-[a-zA-Z]{1}") == false) {
			errors.rejectValue("identificador","pattern.usuario.identificador");
		}
	}

}
