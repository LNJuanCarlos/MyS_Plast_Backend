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

import com.mysplast.springboot.backend.model.entity.Recetaprod;
import com.mysplast.springboot.backend.model.service.RecetaprodService;

@CrossOrigin(origins= {"http://localhost:4200"})
@Controller
@RequestMapping("/recetaprod")
public class RecetaprodController {
	
	@Autowired
	private RecetaprodService recetaprodservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarRecetaprods(){
		
		List<Recetaprod> recetaprods = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			recetaprods = recetaprodservice.listarRecetaprods();
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Recetaprod>>(recetaprods, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id){
		
		Recetaprod recetaprod = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			recetaprod = recetaprodservice.buscarRecetaprodxID(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(recetaprod == null) {
			response.put("mensaje", "La Receta de Producción con el ID: ".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Recetaprod>(recetaprod, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable String id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			recetaprodservice.eliminarRecetaprodxID(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha eliminado la Receta de Producción correctamente!");
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PostMapping("/crear")
	public ResponseEntity<?> crearRecetaprod(@RequestBody Recetaprod recetaprod){
	
		Recetaprod nuevarecetaprod = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		
		try {
			recetaprod.setESTADO("A");
			recetaprod.setREG_USER(authentication.getName());
			recetaprod.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
			nuevarecetaprod =	recetaprodservice.grabarRecetaprod(recetaprod);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado correctamente la Receta de Producción!");
		response.put("recetaprod", nuevarecetaprod);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizarRecetaprod(@PathVariable String id){
		
		Recetaprod recetaprodActual = recetaprodservice.buscarRecetaprodxID(id);
		Recetaprod recetaprodActualizada =  null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		

		if(recetaprodActual.getESTADO() == "N") {
			response.put("mensaje", "El Recetaprod con el ID: ".concat(recetaprodActual.getID_RECETA().toString().concat("ya se encuentra anulada!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		try {
			recetaprodActual.setESTADO("N");
			recetaprodActual.setMOD_USER(authentication.getName());
			recetaprodActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			recetaprodActualizada = recetaprodservice.grabarRecetaprod(recetaprodActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha actualizado correctamente la Receta de Producción!");
		response.put("recetaprod", recetaprodActualizada);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/filtrar-recetaprod/{producto}")
	public ResponseEntity<?> buscarRecetaprodxProducto(@PathVariable String producto) {

		Recetaprod recetaprods = null;

		Map<String, Object> response = new HashMap<>();

		if (producto.equals("")) {
			response.put("mensaje", "Tiene que ingresar el término!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			recetaprods = recetaprodservice.buscarRecetaxProducto(producto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Recetaprod>(recetaprods, HttpStatus.OK);
	}
	
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/filtro")
	public ResponseEntity<?> filtroRecetaprods(@RequestParam(value = "nroreceta", required = false) String nroreceta,
			@RequestParam(value = "producto", required = false) String producto,
			@RequestParam(value = "nomreceta", required = false) String nomreceta) {

		List<Recetaprod> filtrorecetaprods = null;

		Map<String, Object> response = new HashMap<>();

		if ( nroreceta.equals("") && producto.equals("") && nomreceta.equals("")) {
			response.put("mensaje", "Tiene que ingresar al menos un dato!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (nroreceta.equals("")) {
			nroreceta = null;
		}
		if (producto.equals("")) {
			producto = null;
		}
		if (nomreceta.equals("")) {
			nomreceta = null;
		}

		try {
			filtrorecetaprods = recetaprodservice.filtroRecetaprods(nroreceta, producto, nomreceta);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Recetaprod>>(filtrorecetaprods, HttpStatus.OK);
	}

}
