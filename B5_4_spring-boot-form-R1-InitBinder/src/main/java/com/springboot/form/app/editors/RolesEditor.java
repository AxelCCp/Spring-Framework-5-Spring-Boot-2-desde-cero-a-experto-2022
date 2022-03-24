package com.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.form.app.services.IRoleService;

@Component
public class RolesEditor extends PropertyEditorSupport{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Integer id = Integer.parseInt(text);
			setValue(service.obtenerPorId(id));
		}catch(NumberFormatException e) {
			setValue(null);
		}
	}
	
	@Autowired
	private IRoleService service;
}
