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

import com.mysplast.springboot.backend.model.entity.Unmedida;
import com.mysplast.springboot.backend.model.service.UnmedidaService;

@CrossOrigin(origins= {"http://localhost:4200"})
@Controller
@RequestMapping("/unmedida")
public class UnmedidaController {
	
	@Autowired
	private UnmedidaService unmedidaservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarUnmedidas(){
		
		List<Unmedida> unmedidas = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			unmedidas = unmedidaservice.listarUnmedidas();
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Unmedida>>(unmedidas, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id){
		
		Unmedida unmedida = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			unmedida = unmedidaservice.buscarUnmedidaxId(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(unmedida == null) {
			response.put("mensaje", "La Unidad de Medida con el ID: ".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Unmedida>(unmedida, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable String id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			unmedidaservice.eliminarUnmedidaxId(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha eliminado la Unidad de Medida correctamente!");
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PostMapping("/crear")
	public ResponseEntity<?> crearUnmedida(@RequestBody Unmedida unmedida){
	
		Unmedida nuevaunmedida = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			nuevaunmedida =	unmedidaservice.grabarUnmedida(unmedida);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado correctamente la Unidad de Medida!");
		response.put("unmedida", nuevaunmedida);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarUnmedida(@RequestBody Unmedida unmedida){
		
		Unmedida unmedidaActual = unmedidaservice.buscarUnmedidaxId(unmedida.getID_UNMEDIDA());
		Unmedida unmedidaActualizada =  null;
		
		Map<String, Object> response = new HashMap<>();
		

		if(unmedidaActual == null) {
			response.put("mensaje", "La Unidad de Medida con el ID: ".concat(unmedida.getID_UNMEDIDA().toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		try {
			unmedidaActual.setNOM_UNMEDIDA(unmedida.getNOM_UNMEDIDA());
			unmedidaActualizada = unmedidaservice.grabarUnmedida(unmedidaActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha actualizado correctamente la Unidad de Medida!");
		response.put("unmedida", unmedidaActualizada);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}

}
