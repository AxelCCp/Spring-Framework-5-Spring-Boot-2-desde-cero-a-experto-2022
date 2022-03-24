package com.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.springboot.form.app.editors.NombreMayusculaEditor;
import com.springboot.form.app.editors.PaisPropertyEditor;
import com.springboot.form.app.editors.RolesEditor;
import com.springboot.form.app.models.domain.Pais;
import com.springboot.form.app.models.domain.Role;
import com.springboot.form.app.models.domain.Usuario;
import com.springboot.form.app.services.IRoleService;
import com.springboot.form.app.services.PaisService;
import com.springboot.form.app.validation.UsuarioValidador;


//CLASE49//1
//CLASE50//2
//CLASE51//3
//CLASE55//4
//CLASE56//5

@Controller
@SessionAttributes("usuario") //CLASE57
public class FormController {

	//1
	@GetMapping("/form1")
	public String form1(Model model) {
		model.addAttribute("titulo","Formulario 1");
		return "form1";
	}
	@PostMapping("/form1")
	public String procesar1(Model model, 
			@RequestParam(name="username") String username,
			@RequestParam(name="password") String password,
			@RequestParam(name="email") String email) {
		model.addAttribute("titulo","Resultado del formulario 1");
		model.addAttribute("username",username);
		model.addAttribute("password",password);
		model.addAttribute("email",email);
		return "resultado1";
	}
	
	//2
	@GetMapping("/form2")
	public String form2(Model model) {
		model.addAttribute("titulo","Formulario 2");
		return "form2";
	}
	@PostMapping("/form2")
	public String procesar2(Model model, 
			@RequestParam(name="username") String username,
			@RequestParam(name="password") String password,
			@RequestParam(name="email") String email) {
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setEmail(email);
		usuario.setPassword(password);
		model.addAttribute("titulo","Resultado del formulario 2");
		model.addAttribute("usuario",usuario);
		return "resultado2";
	}
	
	//3
	@GetMapping("/form3")
	public String form3(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("titulo","Formulario 3");
		model.addAttribute("usuario",usuario);
		return "form3";
	}
	@PostMapping("/form3")
	public String procesar3(Model model, @Valid Usuario usuario, BindingResult result) {
		model.addAttribute("titulo","Resultado del formulario 3");
		if(result.hasErrors()) {
			Map<String,String>errores = new HashMap<String,String>();
			result.getFieldErrors().forEach(error -> {
				errores.put(error.getField(), "El campo ".concat(error.getField()).concat(" ").concat(error.getDefaultMessage()));
			});
			model.addAttribute("errores",errores);
			return "form3";
		}
		model.addAttribute("usuario",usuario);
		return "resultado3";
	}
	//4
	@GetMapping("/form4")
	public String form4(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("titulo","Formulario 4");
		model.addAttribute("usuario",usuario);
		return "form4";
	}
	@PostMapping("/form4")
	public String procesar4(Model model, @Valid Usuario usuario, BindingResult result) {
		model.addAttribute("titulo","Resultado del formulario 4");
		if(result.hasErrors()) {
			return "form4";
		}
		model.addAttribute("usuario",usuario);
		return "resultado4";
	}
	//-------------------------------------------------------------------------------B1_4
	//5
		@GetMapping("/form5")
		public String form5(Model model) {
			Usuario usuario = new Usuario();
			usuario.setNombre("john");
			usuario.setApellido("doe");
			usuario.setIdentificador("123.456.789-k");
			usuario.setHabilitar(true);
			usuario.setValorSecreto("Algún valor secreto_****");
			model.addAttribute("titulo","Formulario 5");
			model.addAttribute("usuario",usuario);
			return "form5";
		}
		@PostMapping("/form5")
		public String procesar5(Model model, @Valid Usuario usuario, BindingResult result, SessionStatus status) {
			//validator.validate(usuario, result);
			model.addAttribute("titulo","Resultado del formulario 5");
			if(result.hasErrors()) {
				return "form5";
			}
			model.addAttribute("usuario",usuario);
			status.setComplete();
			return "resultado5";
		}
		
		
		//CLASE63
		@InitBinder
		public void initBinder(WebDataBinder binder) {
			binder.addValidators(validator);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dddd");
			dateFormat.setLenient(false);
			binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
			
			//binder.registerCustomEditor(String.class, new NombreMayusculaEditor());
			binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
			//CLASE77
			binder.registerCustomEditor(Pais.class, "pais", paisEditor);
			//CLASE81
			binder.registerCustomEditor(Role.class, "roles", roleEditor);
			
		}
		
		//CLASE76
		@ModelAttribute("listaPaises")
		public List<Pais>listaPaises(){
			return paisService.listar();
		}
		
		/*
		//CLASE75
		@ModelAttribute("listaPaises")
		public List<Pais>listaPaises(){
			return Arrays.asList(
					new Pais(1,"ES","EEUU"),
					new Pais(2,"MX","MÉXICO"),
					new Pais(3,"CA","CANADA"),
					new Pais(4,"FR","FRANCIA"),
					new Pais(5,"COL","COLOMBIA"));
		}*/
		
		/*
		//CLASE74
		@ModelAttribute("paisesMap")
		public Map<String,String>paisesMap(){
			Map<String,String>paises = new HashMap(); 
			paises.put("USA", "EEUU");
			paises.put("MX", "MÉXICO");
			paises.put("CA", "CANADA");
			paises.put("FR", "FRANCIA");
			paises.put("COL", "COLOMBIA");
			return paises;
		}*/
		
		/*
		//CLASE72
		@ModelAttribute("paises")
		public List<String>paises(){
			return Arrays.asList("EEUU",
					"MÉXICO",
					"CANADA",
					"FRANCIA",
					"COLOMBIA");
		}*/
		
		//CLASE78
		@ModelAttribute("listaRolesString")
		public List<String>listaRolesString(){
			List<String>roles = new ArrayList<>(); 
			roles.add("ROLE_ADMIN");
			roles.add("ROLE_USER");
			roles.add("ROLE_MODERATOR");
			return roles;
		}
		
		//CLASE79
		@ModelAttribute("listaRolesMap")
		public Map<String,String>listadoRolesMap(){
			Map<String,String>roles = new HashMap<String,String>(); 
			roles.put("ROLE_ADMIN", "Administrador");
			roles.put("ROLE_USER", "Usuario");
			roles.put("ROLE_MODERATOR", "Moderador");
			return roles;
		}
		
		@ModelAttribute("listaRoles")
		public List<Role>listaRoles(){
			return this.roleService.listar();
		}
		
		
		//CLASE62
		@Autowired
		private UsuarioValidador validator;
		//CLASE76
		@Autowired
		private PaisService paisService;
		//CLASE77
		@Autowired
		private PaisPropertyEditor paisEditor;
		//CLASE80
		@Autowired
		private IRoleService roleService;
		@Autowired
		private RolesEditor roleEditor;

	
}
