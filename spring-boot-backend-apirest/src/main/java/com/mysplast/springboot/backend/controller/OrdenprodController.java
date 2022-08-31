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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysplast.springboot.backend.model.entity.Ordenprod;
import com.mysplast.springboot.backend.model.service.OrdenprodService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/ordenprod")
public class OrdenprodController {
	
	@Autowired
	private OrdenprodService ordenprodservice;
	
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/listartop")
	public ResponseEntity<?> listarTop50() {

		List<Ordenprod> ordenprods = null;

		Map<String, Object> response = new HashMap<>();

		try {
			ordenprods = ordenprodservice.listarOrdenprod();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Ordenprod>>(ordenprods, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id) {

		Ordenprod ordenprod = null;
		Map<String, Object> response = new HashMap<>();

		try {

			ordenprod = ordenprodservice.buscarOrdenprodXId(id);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (ordenprod == null) {
			response.put("mensaje", "La Orden de Produccción con el ID:".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Ordenprod>(ordenprod, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_LOGISTICA" })
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable String id) {

		Map<String, Object> response = new HashMap<>();

		try {
			ordenprodservice.eliminarOrdenprodxID(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la eliminación en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Orden de Produccción ha sido eliminada con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@Secured({ "ROLE_ADMIN", "ROLE_LOGISTICA" })
	@PostMapping("/ordenprod")
	public ResponseEntity<?> crear(@RequestBody Ordenprod ordenprod) {

		Ordenprod nuevoordenprod = null;
		Map<String, Object> response = new HashMap<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		try {
			ordenprod.setESTADO("P");
			ordenprod.setREG_USER(authentication.getName());
			ordenprod.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
			nuevoordenprod = ordenprodservice.grabarOrdenprod(ordenprod);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Orden de Produccción ha sido registrada con éxito!");
		response.put("ordenprod", nuevoordenprod);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_LOGISTICA" })
	@PutMapping("/estado/{id}")
	public ResponseEntity<?> actualizarEstadoOrdenprod(@RequestBody Ordenprod ordenprod,
			@PathVariable String id) {
		Ordenprod ordenprodActual = ordenprodservice.buscarOrdenprodXId(id);
		Ordenprod ordenprodActualizada = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();

		if (ordenprodActual == null) {
			response.put("mensaje", "La Orden de Produccción con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordenprodActual.getESTADO().equals("C")) {
			response.put("mensaje", "La Orden de Produccción con el ID:" + id.toString()
					+ "se encuentra confirmada, comuníquese con el encargado de produccción!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordenprodActual.getESTADO().equals("N")) {
			response.put("mensaje", "La Orden de Produccción con el ID:" + id.toString() + "ya está anulada!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			ordenprodActual.setESTADO(ordenprod.getESTADO());
			ordenprodActual.setMOD_USER(authentication.getName());
			ordenprodActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			ordenprodActualizada = ordenprodservice.grabarOrdenprod(ordenprodActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la orden de producción!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Orden de Produccción ha sido actualizada con éxito!");
		response.put("ordenprod", ordenprodActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" })
	@PutMapping("/inventariar/{id}")
	public ResponseEntity<?> inventariarEstadoOrdenprod(@RequestBody Ordenprod ordenprod,
			@PathVariable String id) {
		Ordenprod ordenprodActual = ordenprodservice.buscarOrdenprodXId(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Ordenprod ordenprodActualizada = null;
		Map<String, Object> response = new HashMap<>();

		if (ordenprodActual == null) {
			response.put("mensaje", "La Orden de Producción con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordenprodActual.getESTADO().equals("I")) {
			response.put("mensaje", "La Orden de Producción con el ID:" + id.toString() + "ya se encuentra inventariada!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordenprodActual.getESTADO().equals("N")) {
			response.put("mensaje",
					"La Orden de Producción con el ID:" + id.toString() + "se encuentra anulada, no se puede inventariar!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			ordenprodActual.setESTADO("I");
			ordenprodActual.setMOD_USER(authentication.getName());
			ordenprodActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			ordenprodActualizada = ordenprodservice.grabarOrdenprod(ordenprodActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la orden de compra!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Orden de Producción ha sido actualizada con éxito!");
		response.put("ordenprod", ordenprodActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" })
	@PutMapping("/aprobar/{id}")
	public ResponseEntity<?> aprobarEstadoOrdenprod(@RequestBody Ordenprod ordenprod,
			@PathVariable String id) {
		Ordenprod ordenprodActual = ordenprodservice.buscarOrdenprodXId(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Ordenprod ordenprodActualizada = null;
		Map<String, Object> response = new HashMap<>();

		if (ordenprodActual == null) {
			response.put("mensaje", "La Orden de Producción con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordenprodActual.getESTADO().equals("A")) {
			response.put("mensaje", "La Orden de Producción con el ID:" + id.toString() + "ya se encuentra aprobada!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordenprodActual.getESTADO().equals("N")) {
			response.put("mensaje",
					"La Orden de Producción con el ID:" + id.toString() + "se encuentra anulada, no se puede aprobar!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			ordenprodActual.setESTADO("A");
			ordenprodActual.setMOD_USER(authentication.getName());
			ordenprodActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			ordenprodActualizada = ordenprodservice.grabarOrdenprod(ordenprodActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la orden de compra!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Orden de Producción ha sido actualizada con éxito!");
		response.put("ordenprod", ordenprodActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/filtro")
	public ResponseEntity<?> filtroOrdenprods(@RequestParam(value = "subalmacen", required = false) String subalmacen,
			@RequestParam(value = "almacen", required = false) String almacen,
			@RequestParam(value = "fecha1", required = false) String fecha1,
			@RequestParam(value = "fecha2", required = false) String fecha2,
			@RequestParam(value = "estado", required = false) String estado) {

		List<Ordenprod> filtroordenprods = null;

		Map<String, Object> response = new HashMap<>();

		if (subalmacen.equals("") && almacen.equals("") && fecha1.equals("") && fecha2.equals("") && estado.equals("")) {
			response.put("mensaje", "Tiene que ingresar al menos un dato!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (fecha1 != "" && fecha2.equals("") || fecha1.equals("") && fecha2 != "") {
			response.put("mensaje", "Si va a filtrar por fechas debe escoger un rango de fechas!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (subalmacen.equals("")) {
			subalmacen = null;
		}
		if (almacen.equals("")) {
			almacen = null;
		}
		if (fecha1.equals("")) {
			fecha1 = null;
		}
		if (fecha2.equals("")) {
			fecha2 = null;
		}
		
		if (estado.equals("")) {
			estado = null;
		}

		try {
			filtroordenprods = ordenprodservice.filtroOrdenprod(subalmacen, almacen, fecha1, fecha2, estado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Ordenprod>>(filtroordenprods, HttpStatus.OK);

	}

}
