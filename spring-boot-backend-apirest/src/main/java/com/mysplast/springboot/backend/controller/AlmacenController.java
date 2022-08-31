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

import com.mysplast.springboot.backend.model.entity.Almacen;
import com.mysplast.springboot.backend.model.service.AlmacenService;

@CrossOrigin(origins= {"http://localhost:4200"})
@Controller
@RequestMapping("/almacen")
public class AlmacenController {

	@Autowired
	private AlmacenService almacenservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarAlmacenes(){
		
		List<Almacen> almacens = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			almacens = almacenservice.listarAlmacenes();
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Almacen>>(almacens, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id){
		
		Almacen almacen = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			almacen = almacenservice.buscarAlmacenxId(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(almacen == null) {
			response.put("mensaje", "El Almacen con el ID: ".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Almacen>(almacen, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable String id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			almacenservice.eliminarAlmacenxId(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha eliminado el Almacén correctamente!");
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PostMapping("/crear")
	public ResponseEntity<?> crearAlmacen(@RequestBody Almacen almacen){
	
		Almacen nuevaalmacen = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		
		try {
			almacen.setESTADO("A");
			almacen.setREG_USER(authentication.getName());
			almacen.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
			nuevaalmacen =	almacenservice.crearAlmacen(almacen);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado correctamente el Almacén!");
		response.put("almacen", nuevaalmacen);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarAlmacen(@RequestBody Almacen almacen){
		
		Almacen almacenActual = almacenservice.buscarAlmacenxId(almacen.getID_ALMACEN());
		Almacen almacenActualizada =  null;
		
		Map<String, Object> response = new HashMap<>();
		

		if(almacenActual == null) {
			response.put("mensaje", "El Almacén con el ID: ".concat(almacen.getID_ALMACEN().toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		try {
			almacenActual.setNOM_ALMACEN(almacen.getNOM_ALMACEN());
			almacenActual.setESTADO(almacen.getESTADO());
			almacenActualizada = almacenservice.crearAlmacen(almacenActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha actualizado correctamente el Almacen!");
		response.put("almacen", almacenActualizada);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}

}
