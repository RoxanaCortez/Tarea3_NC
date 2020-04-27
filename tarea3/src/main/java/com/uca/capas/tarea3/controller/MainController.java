package com.uca.capas.tarea3.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping("/ingresar")
	public String ingresar() {
	
		return "/ingresar";
	}

	@RequestMapping("/registro")
	public ModelAndView registro(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		String nombres = request.getParameter("nombre");
		String apellidos = request.getParameter("apellido");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date nacimiento = formatter.parse(request.getParameter("fNacimiento"));
		String lugar = request.getParameter("lNacimiento");
		String colegio = request.getParameter("colegio");
		String telefono  = request.getParameter("telefono");
		String celular = request.getParameter("celular");
		
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(nacimiento);
		Integer anio = calendar.get(Calendar.YEAR);
		
		String aIngresado = "Alumno ingresado con éxito";
		//Si no hay errores mostrará página resultado con un mensaje "Alumno ingresado con éxito"
		mav.addObject("ingresado", aIngresado);
		mav.setViewName("/resultado");
	
		//Lista a la que se le agregaran los errores encontrados		
		List<String> listaErrores = new ArrayList<>();
		
		//Campo Nombres
		if (nombres.isEmpty() == true) { 
			listaErrores.add("El campo Nombres no puede estar vacío.");
		}
		if (nombres.length() > 25) {
			listaErrores.add("El campo Nombres no puede ser mayor a 25 caracteres.");
		}
		
		//Campo Apellidos
		if (apellidos.isEmpty() == true) {
			listaErrores.add("El campo Apellidos no puede estar vacío.");
		}
		if (apellidos.length() > 25) {
			listaErrores.add("El campo Apellidos no puede ser mayor a 25 caracteres.");
		}
		
		//Campo Fecha de Nacimiento
		if (anio < 2003) {
			listaErrores.add("La Fecha de nacimiento no puede ser menor al 1 de Enero de 2003.");
		}
	
		//Campo Lugar de Nacimiento
		if (lugar.isEmpty() == true) { 
			listaErrores.add("El campo Lugar de nacimiento no puede estar vacío.");
		}
		if (lugar.length() > 25) { 
			listaErrores.add("El campo Lugar no puede ser mayor a 25 caracteres.");
		}
		
		//Campo Instituto/Colegio
		if (colegio.isEmpty() == true) { 
			listaErrores.add("El campo Instituto o Colegio de procedencia no puede estar vacío."); 
		}
		if (colegio.length() > 100) {
			listaErrores.add("El campo Instituto o Colegio de procedencia no puede ser mayor a 100 caracteres."); 
		}
		
		//Campo Teléfono
		if (telefono.length() != 8) {
			listaErrores.add("El campo Teléfono fijo debe tener exactamente 8 caracteres.");		
		}
		
		//Campo Celular
		if (celular.length() != 8) {
			listaErrores.add(" El campo Teléfono móvil  debe tener exactamente 8 caracteres.");
		}

		if (listaErrores.isEmpty()==false) {  //Si hay errores (algun error se ha agregado a la lista de errores), se mostrara pagina errores
			//quitando corchetes y reemplazando comas por saltos de línea
			String errores = listaErrores.toString().replaceAll("(^\\[|\\]$)", "").replaceAll(",", "<br />"); 
			mav.addObject("list", errores);
			mav.setViewName("/errores");
		}
		
		return mav;
	}
}
