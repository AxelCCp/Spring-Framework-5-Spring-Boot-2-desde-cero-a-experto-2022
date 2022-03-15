package com.springboot.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.app.models.Usuario;


@Controller
@RequestMapping("/app")
public class IndexController {

	
	@GetMapping("/model")
	public String index(Model model) {
		model.addAttribute("titulo","Hola Spring framework con model");
		return "index";	
	}
	
	@GetMapping("/modelmap")
	public String index(ModelMap modelmap) {
		modelmap.addAttribute("titulo","Hola Spring framework con ModelMap");
		return "index";
	}
	
	@GetMapping({"/map"})
	public String index(Map<String,Object> map) {
		map.put("titulo","Hola Spring framework con map");
		return "index";
	}
	
	@GetMapping({"/modelandview"})
	public ModelAndView index(ModelAndView mv) {
		mv.addObject("titulo","Hola Spring framework con ModelAndView");
		mv.setViewName("index");
		return mv;
	}
	
	@GetMapping("/perfil")
	public String perfil(Model model){
		Usuario usuario = new Usuario();
		usuario.setNombre("Andrés");
		usuario.setApellido("Guzmán");
		usuario.setEmail("sdfsdf@skjfdhs.com");
		model.addAttribute("usuario",usuario);
		model.addAttribute("titulo","Perfil del usuario: ".concat(usuario.getNombre()));
		return "perfil";
	}
	
	/*
	@GetMapping("/listar")
	public String listar(Model model){
		List<Usuario>usuarios = new ArrayList<>();
		usuarios.add(new Usuario("xxxxx","ssss","aes@asda.com"));
		usuarios.add(new Usuario("ttttt","iiii","ttt@asda.com"));
		usuarios.add(new Usuario("jjjjj","ooooo","jjj@asda.com"));
		model.addAttribute("titulo", "Listado de Usuarios");
		model.addAttribute("usuarios",usuarios);
		return "listar";
	}
	*/
	
	
	@GetMapping("/listar")
	public String listar(Model model){
		model.addAttribute("titulo", "Listado de Usuarios");
		return "listar";	
	}
	@ModelAttribute("usuarios")
	public List<Usuario>poblarUsuarios(){
		List<Usuario>usuarios = new ArrayList<>();
		usuarios.add(new Usuario("xxxxx","ssss","aes@asda.com"));
		usuarios.add(new Usuario("ttttt","iiii","ttt@asda.com"));
		usuarios.add(new Usuario("jjjjj","ooooo","jjj@asda.com"));
		return usuarios;
	}
	
	
	@Value("${texto.index}")
	private String textoIndex;
	@Value("$")
	private String textoPerfil;
	@Value("$")
	private String textoListar;
	
	
}
