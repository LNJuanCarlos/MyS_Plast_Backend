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

import com.mysplast.springboot.backend.model.entity.Categoria;
import com.mysplast.springboot.backend.model.service.CategoriaService;

@CrossOrigin(origins= {"http://localhost:4200"})
@Controller
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarCategorias(){
		
		List<Categoria> categorias = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			categorias = categoriaservice.listarCategorias();
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Categoria>>(categorias, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id){
		
		Categoria categoria = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			categoria = categoriaservice.buscarCategoriaxId(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(categoria == null) {
			response.put("mensaje", "La categoria con el ID: ".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable String id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			categoriaservice.eliminarCategoria(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha eliminado la categoria correctamente!");
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PostMapping("/crear")
	public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria){
	
		Categoria nuevacategoria = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			categoria.setREG_USER(authentication.getName());
			categoria.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
			nuevacategoria =	categoriaservice.grabarCategoria(categoria);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado correctamente la categoria!");
		response.put("categoria", nuevacategoria);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarCategoria(@RequestBody Categoria categoria){
		
		Categoria categoriaActual = categoriaservice.buscarCategoriaxId(categoria.getID_CATEGORIA());
		Categoria categoriaActualizada =  null;
		
		Map<String, Object> response = new HashMap<>();
		

		if(categoriaActual == null) {
			response.put("mensaje", "La categoria con el ID: ".concat(categoria.getID_CATEGORIA().toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		try {
			categoriaActual.setNOM_CATEGORIA(categoria.getNOM_CATEGORIA());
			categoriaActualizada = categoriaservice.grabarCategoria(categoriaActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha actualizado correctamente la categoria!");
		response.put("categoria", categoriaActualizada);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}

}
