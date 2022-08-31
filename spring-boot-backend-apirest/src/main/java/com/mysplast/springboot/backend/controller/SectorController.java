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

import com.mysplast.springboot.backend.model.entity.Sector;
import com.mysplast.springboot.backend.model.service.SectorService;

@CrossOrigin(origins= {"http://localhost:4200"})
@Controller
@RequestMapping("/sector")
public class SectorController {
	
	@Autowired
	private SectorService sectorservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarSectores(){
		
		List<Sector> sectores = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			sectores = sectorservice.listarSectores();
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Sector>>(sectores, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id){
		
		Sector sector = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			sector = sectorservice.buscarSectorxID(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(sector == null) {
			response.put("mensaje", "El Sector con el ID: ".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Sector>(sector, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable String id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			sectorservice.eliminarSectorxID(id);
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
	public ResponseEntity<?> crearSector(@RequestBody Sector sector){
	
		Sector nuevasector = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		
		try {
			sector.setREG_USER(authentication.getName());
			sector.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
			nuevasector =	sectorservice.grabarSector(sector);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado correctamente el Sector!");
		response.put("sector", nuevasector);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarSector(@RequestBody Sector sector){
		
		Sector sectorActual = sectorservice.buscarSectorxID(sector.getID_SECTOR());
		Sector sectorActualizada =  null;
		
		Map<String, Object> response = new HashMap<>();
		

		if(sectorActual == null) {
			response.put("mensaje", "El Almacén con el ID: ".concat(sector.getID_SECTOR().toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		try {
			sectorActual.setNOM_SECTOR(sector.getNOM_SECTOR());
			sectorActual.setId_ALMACEN(sector.getId_ALMACEN());
			sectorActualizada = sectorservice.grabarSector(sectorActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha actualizado correctamente el Sector!");
		response.put("sector", sectorActualizada);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/buscar/almacen/{alm}")
	public ResponseEntity<?> buscarPorAlmacén(@PathVariable String alm) {

		List<Sector> sectores = null;

		Map<String, Object> response = new HashMap<>();

		try {
			sectores = sectorservice.buscarPorAlmacen(alm);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (sectores == null || sectores.isEmpty()) {
			response.put("mensaje", "No existe sectores con el almacén ingresado");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Sector>>(sectores, HttpStatus.OK);
	}

}
