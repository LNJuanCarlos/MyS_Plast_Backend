package com.mysplast.springboot.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysplast.springboot.backend.model.entity.Centrocosto;
import com.mysplast.springboot.backend.model.service.CentrocostoService;

@CrossOrigin(origins= {"http://localhost:4200"})
@Controller
@RequestMapping("/centrocosto")
public class CentrocostoController {
	
	@Autowired
	private CentrocostoService centrocostoservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarCentrocostos(){
		
		List<Centrocosto> centrocostos = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			centrocostos = centrocostoservice.listarCentroCostos();
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Centrocosto>>(centrocostos, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id){
		
		Centrocosto centrocosto = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			centrocosto = centrocostoservice.buscarCentroCostoxId(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(centrocosto == null) {
			response.put("mensaje", "El Centro de Costo con el ID: ".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Centrocosto>(centrocosto, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			centrocostoservice.eliminarCentroCostoxId(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha eliminado el Centro de Costo correctamente!");
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PostMapping("/crear")
	public ResponseEntity<?> crearCentrocosto(@RequestBody Centrocosto centrocosto){
	
		Centrocosto nuevacentrocosto = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			nuevacentrocosto =	centrocostoservice.grabarCentroCosto(centrocosto);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado correctamente el Centro de Costo!");
		response.put("centrocosto", nuevacentrocosto);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarCentrocosto(@RequestBody Centrocosto centrocosto){
		
		Centrocosto centrocostoActual = centrocostoservice.buscarCentroCostoxId(centrocosto.getID_CENTROCOSTO());
		Centrocosto centrocostoActualizada =  null;
		
		Map<String, Object> response = new HashMap<>();
		

		if(centrocostoActual == null) {
			response.put("mensaje", "El Centro de Costo con el ID: ".concat(centrocosto.getID_CENTROCOSTO().toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		try {
			centrocostoActual.setNOM_CENTROCOSTO(centrocosto.getNOM_CENTROCOSTO());
			centrocostoActualizada = centrocostoservice.grabarCentroCosto(centrocostoActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha actualizado correctamente el Centro de Costo!");
		response.put("centrocosto", centrocostoActualizada);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}


}
