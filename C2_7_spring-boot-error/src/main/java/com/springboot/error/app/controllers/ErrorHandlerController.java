package com.springboot.error.app.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //CON ESTA SE MAPEA A UNA EXCEPTION Y NO A UNA RUTA.
public class ErrorHandlerController {
	
	@ExceptionHandler(ArithmeticException.class )//SE MAPEA A UNA EXCEPTION
	public String aritmeticaError(Exception ex, Model model) {
		model.addAttribute("error","Error de aritmética");
		model.addAttribute("message",ex.getMessage());
		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp",new Date());
		return "error/aritmetica";
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public String numberFormatException(NumberFormatException ex, Model model) {
		model.addAttribute("error","Formato de número inválido!");
		model.addAttribute("message",ex.getMessage());
		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp",new Date());
		return "error/numeroFormato";
	}

}
