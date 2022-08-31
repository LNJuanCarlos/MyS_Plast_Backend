package com.mysplast.springboot.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysplast.springboot.backend.model.entity.Categoriatransaccion;
import com.mysplast.springboot.backend.model.service.CategoriatransaccionService;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/categoriatransaccion")
public class CategoriatransaccionController {
	
	@Autowired
	private CategoriatransaccionService categoriatransaccionservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/tipotrans/{tipotrans}")
	public ResponseEntity<?>buscarPorTipoTrans(@PathVariable Long tipotrans){
		
		List<Categoriatransaccion> categoriatransaccions = null;
		
		System.out.println(tipotrans);

		Map<String, Object> response = new HashMap<>();
		
		if(tipotrans == null) {
			response.put("mensaje", "Debe ingresar a menos un término!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			try {
				categoriatransaccions = categoriatransaccionservice.buscarPorTipoTransaccion(tipotrans);
			} catch (NoSuchElementException e) {
				response.put("mensaje", "La SubFamilia con la familia Ingresada no existe!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Categoriatransaccion>>(categoriatransaccions, HttpStatus.OK);

}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId (@PathVariable Long id) {
		
		Categoriatransaccion categoriatransaccions = null;

		Map<String, Object> response = new HashMap<>();

		try {
			categoriatransaccions = categoriatransaccionservice.buscarCaterogiraTransaccion(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(categoriatransaccions == null) {
			response.put("mensaje", "El Centro de Costo con el ID:".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Categoriatransaccion>(categoriatransaccions, HttpStatus.OK);
	}
	
	

}
