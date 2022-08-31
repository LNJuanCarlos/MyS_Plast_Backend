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

import com.mysplast.springboot.backend.model.entity.Natural;
import com.mysplast.springboot.backend.model.service.NaturalService;

@CrossOrigin(origins= {"http://localhost:4200"})
@Controller
@RequestMapping("/natural")
public class NaturalController {
	
	@Autowired
	private NaturalService naturalservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarNaturales(){
		
		List<Natural> naturales = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			naturales = naturalservice.listarNaturales();
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Natural>>(naturales, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id){
		
		Natural natural = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			natural = naturalservice.buscarNaturalxID(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(natural == null) {
			response.put("mensaje", "La Persona Natural con el ID: ".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Natural>(natural, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable String id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			naturalservice.eliminarNaturalxID(id);
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
	public ResponseEntity<?> crearNatural(@RequestBody Natural natural){
	
		Natural nuevanatural = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		
		try {
			natural.setESTADO("A");
			natural.setREG_USER(authentication.getName());
			natural.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
			nuevanatural =	naturalservice.grabarNatural(natural);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado correctamente la Persona Jurídica!");
		response.put("natural", nuevanatural);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarNatural(@RequestBody Natural natural){
		
		Natural naturalActual = naturalservice.buscarNaturalxID(natural.getID_PERSONA());
		Natural naturalActualizada =  null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		

		if(naturalActual == null) {
			response.put("mensaje", "La persona jurídica con el ID: ".concat(natural.getID_PERSONA().toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		try {
			naturalActual.setCORREO(natural.getCORREO());
			naturalActual.setDIRECCION(natural.getDIRECCION());
			naturalActual.setNOMBRES(natural.getNOMBRES());
			naturalActual.setId_DISTRITO(natural.getId_DISTRITO());
			naturalActual.setNRODOC(natural.getNRODOC());
			naturalActual.setFLAG_TRABAJADOR(natural.getFLAG_TRABAJADOR());
			naturalActual.setTELEFONO(natural.getTELEFONO());
			naturalActual.setAPE_PAT(natural.getAPE_PAT());
			naturalActual.setESTADO(natural.getESTADO());
			naturalActual.setAPE_MAT(natural.getAPE_MAT());
			naturalActual.setMOD_USER(authentication.getName());
			naturalActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			naturalActualizada = naturalservice.grabarNatural(naturalActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha actualizado correctamente el Natural!");
		response.put("natural", naturalActualizada);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/filtro")
	public ResponseEntity<?> filtroNatural(
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "nrodoc", required = false) String nrodoc) {
		
		List<Natural> filtronatural = null;

		Map<String, Object> response = new HashMap<>();
		
		if(nombre.equals("")) {
			nombre = null;
		}
		if(nrodoc.equals("")) {
			nrodoc = null;
		}
	
		try {
			filtronatural = naturalservice.filtroNatural(nombre, nrodoc);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
		
		return new ResponseEntity<List<Natural>>(filtronatural, HttpStatus.OK);
	}

}
