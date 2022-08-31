package com.mysplast.springboot.backend.controller;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysplast.springboot.backend.model.entity.Juridica;
import com.mysplast.springboot.backend.model.service.JuridicaService;

@CrossOrigin(origins= {"http://localhost:4200"})
@Controller
@RequestMapping("/juridica")
public class JuridicaController {
	
	@Autowired
	private JuridicaService juridicaservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarJuridicas(){
		
		List<Juridica> juridicas = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			juridicas = juridicaservice.listarJuridicas();
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Juridica>>(juridicas, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id){
		
		Juridica juridica = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			juridica = juridicaservice.buscarJuridicaxID(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(juridica == null) {
			response.put("mensaje", "La Persona Juridica con el ID: ".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Juridica>(juridica, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable String id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			juridicaservice.eliminarJuridicaxID(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha eliminado la Persona Jurídica correctamente!");
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PostMapping("/crear")
	public ResponseEntity<?> crearJuridica(@RequestBody Juridica juridica){
	
		Juridica nuevajuridica = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		
		try {
			juridica.setESTADO("A");
			juridica.setREG_USER(authentication.getName());
			juridica.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
			nuevajuridica =	juridicaservice.grabarJuridica(juridica);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado correctamente la Persona Jurídica!");
		response.put("juridica", nuevajuridica);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarJuridica(@RequestBody Juridica juridica){
		
		Juridica juridicaActual = juridicaservice.buscarJuridicaxID(juridica.getID_PERSONA());
		Juridica juridicaActualizada =  null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		

		if(juridicaActual == null) {
			response.put("mensaje", "La persona jurídica con el ID: ".concat(juridica.getID_PERSONA().toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		try {
			juridicaActual.setCORREO(juridica.getCORREO());
			juridicaActual.setDIRECCION(juridica.getDIRECCION());
			juridicaActual.setId_ACTIVIDAD(juridica.getId_ACTIVIDAD());
			juridicaActual.setId_DISTRITO(juridica.getId_DISTRITO());
			juridicaActual.setNRODOC(juridica.getNRODOC());
			juridicaActual.setTELEFONO(juridica.getTELEFONO());
			juridicaActual.setREPRESENTANTE(juridica.getREPRESENTANTE());
			juridicaActual.setESTADO(juridica.getESTADO());
			juridicaActual.setRAZONSOCIAL(juridica.getRAZONSOCIAL());
			juridicaActual.setMOD_USER(authentication.getName());
			juridicaActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			juridicaActualizada = juridicaservice.grabarJuridica(juridicaActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha actualizado correctamente el Juridica!");
		response.put("juridica", juridicaActualizada);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/filtro")
	public  ResponseEntity<?> filtroJuridica(
			@RequestParam(value = "nrodoc", required = false) String nrodoc,
			@RequestParam(value = "razsoc", required = false) String razsoc) {
		
		List<Juridica> filtrojuridica = null;

		Map<String, Object> response = new HashMap<>();
		
		if(nrodoc.equals("")) {
			nrodoc = null;
		}
		
		if(razsoc.equals("")) {
			razsoc = null;
		}

		try {
			filtrojuridica = juridicaservice.filtroJuridica(nrodoc, razsoc);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Juridica>>(filtrojuridica, HttpStatus.OK);
	} 


}
