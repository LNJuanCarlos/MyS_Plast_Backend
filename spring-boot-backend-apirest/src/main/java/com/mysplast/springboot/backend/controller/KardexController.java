package com.mysplast.springboot.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysplast.springboot.backend.model.entity.Kardex;
import com.mysplast.springboot.backend.model.service.KardexService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/kardex")
public class KardexController {
	
	@Autowired
	private KardexService kardexservice;
	
	@GetMapping("/listar")
	public List<Kardex> listarTodo(){
		return kardexservice.listarKardex();
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId (@PathVariable Long id) {
		
		Kardex kardex = null;

		Map<String, Object> response = new HashMap<>();

		try {
			kardex = kardexservice.buscarKardexId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(kardex == null) {
			response.put("mensaje", "El kardex con el ID:".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<Kardex>(kardex, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE"})
	@PostMapping("/kardex")
	public ResponseEntity<?> crear(@RequestBody Kardex kardex) {

		Kardex nuevakardex = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			nuevakardex = kardexservice.grabarKardex(kardex);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El kardex ha sido registrado con éxito!");
		response.put("kardex", nuevakardex);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
