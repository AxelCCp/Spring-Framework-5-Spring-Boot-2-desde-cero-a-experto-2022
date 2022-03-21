package com.springboot.form.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentificadorRegexValidador implements ConstraintValidator<IdentificadorRegex,String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value.matches("[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][a-zA-Z]{1}") == false) {
			return true;
		}
		return false;
	}

}
