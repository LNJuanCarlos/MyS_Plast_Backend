package com.mysplast.springboot.backend.controller;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
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

import com.mysplast.springboot.backend.model.entity.Ordencompra;
import com.mysplast.springboot.backend.model.entity.Tipotransaccion;
import com.mysplast.springboot.backend.model.service.OrdencompraService;
import com.mysplast.springboot.backend.model.service.Tipotransaccionservice;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/ordencompra")
public class OrdencompraController {
	
	@Autowired
	private OrdencompraService ordencompraservice;

	@Autowired
	private Tipotransaccionservice tipotransaccionservice;

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/listar")
	public ResponseEntity<?> listarTodo() {

		List<Ordencompra> ordencompras = null;

		Map<String, Object> response = new HashMap<>();

		try {
			ordencompras = ordencompraservice.listarOrdencompras();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Ordencompra>>(ordencompras, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/listartop")
	public ResponseEntity<?> listarTop100() {

		List<Ordencompra> ordencompras = null;

		Map<String, Object> response = new HashMap<>();

		try {
			ordencompras = ordencompraservice.listarTop50Ordencompras();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Ordencompra>>(ordencompras, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id) {

		Ordencompra ordencompra = null;
		Map<String, Object> response = new HashMap<>();

		try {

			ordencompra = ordencompraservice.buscarOrdencomprasId(id);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (ordencompra == null) {
			response.put("mensaje", "El ordencompra con el ID:".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Ordencompra>(ordencompra, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_LOGISTICA" })
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable String id) {

		Map<String, Object> response = new HashMap<>();

		try {
			ordencompraservice.eliminarOrdencompra(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la eliminación en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El ordencompra ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@Secured({ "ROLE_ADMIN", "ROLE_LOGISTICA" })
	@PostMapping("/ordencompra")
	public ResponseEntity<?> crear(@RequestBody Ordencompra ordencompra) {

		Ordencompra nuevoordencompra = null;
		Map<String, Object> response = new HashMap<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Tipotransaccion nuevotipotransaccion = tipotransaccionservice.buscarTipotransaccionId(Long.parseLong("6"));

		try {
			double total = 0;
			double igv = 0;
			double subtotal = 0;

			for (int i = 0; i < ordencompra.getItems().size(); i++) {
				total = (ordencompra.getItems().get(i).getTOTAL()) + total;
			}

			subtotal = total / 1.18;
			igv = total - subtotal;

			ordencompra.setIGV(igv);
			ordencompra.setSUBTOTAL(subtotal);
			ordencompra.setTOTAL(total);
			ordencompra.setId_TIPOTRANSACCION(nuevotipotransaccion);
			ordencompra.setESTADO("P");
			ordencompra.setREG_USER(authentication.getName());
			ordencompra.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
			nuevoordencompra = ordencompraservice.grabarOrdencompra(ordencompra);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El ordencompra ha sido registrado con éxito!");
		response.put("ordencompra", nuevoordencompra);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_LOGISTICA" })
	@PutMapping("/estado/{id}")
	public ResponseEntity<?> actualizarEstadoOrdencompra(@RequestBody Ordencompra ordencompra,
			@PathVariable String id) {
		Ordencompra ordencompraActual = ordencompraservice.buscarOrdencomprasId(id);
		Ordencompra ordencompraActualizada = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();

		if (ordencompraActual == null) {
			response.put("mensaje", "El ordencompra con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordencompraActual.getESTADO().equals("I")) {
			response.put("mensaje", "La Orden de Compra con el ID:" + id.toString()
					+ "se encuentra inventariada, comuníquese con el encargado de almacén!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordencompraActual.getESTADO().equals("N")) {
			response.put("mensaje", "La Orden de Compra con el ID:" + id.toString() + "ya está anulada!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			ordencompraActual.setESTADO("N");
			ordencompraActual.setMOD_USER(authentication.getName());
			ordencompraActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			ordencompraActualizada = ordencompraservice.grabarOrdencompra(ordencompraActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la orden de compra!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El ordencompra ha sido actualizada con éxito!");
		response.put("ordencompra", ordencompraActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_LOGISTICA" })
	@PutMapping("/aprobar/{id}")
	public ResponseEntity<?> aprobarEstadoOrdencompra(@RequestBody Ordencompra ordencompra,
			@PathVariable String id) {
		Ordencompra ordencompraActual = ordencompraservice.buscarOrdencomprasId(id);
		Ordencompra ordencompraActualizada = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();

		if (ordencompraActual == null) {
			response.put("mensaje", "El ordencompra con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordencompraActual.getESTADO().equals("A")) {
			response.put("mensaje", "La Orden de Compra con el ID:" + id.toString()
					+ "se encuentra aprobada, comuníquese con el encargado de almacén!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordencompraActual.getESTADO().equals("N")) {
			response.put("mensaje", "La Orden de Compra con el ID:" + id.toString() + "ya está anulada!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			ordencompraActual.setESTADO("A");
			ordencompraActual.setMOD_USER(authentication.getName());
			ordencompraActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			ordencompraActualizada = ordencompraservice.grabarOrdencompra(ordencompraActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la orden de compra!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El ordencompra ha sido actualizada con éxito!");
		response.put("ordencompra", ordencompraActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" })
	@PutMapping("/inventariar/{id}")
	public ResponseEntity<?> inventariarEstadoOrdencompra(@RequestBody Ordencompra ordencompra,
			@PathVariable String id) {
		Ordencompra ordencompraActual = ordencompraservice.buscarOrdencomprasId(id);
		Ordencompra ordencompraActualizada = null;
		Map<String, Object> response = new HashMap<>();

		if (ordencompraActual == null) {
			response.put("mensaje", "El ordencompra con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordencompraActual.getESTADO().equals("I")) {
			response.put("mensaje", "La Orden de Compra con el ID:" + id.toString() + "ya se encuentra inventariada!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ordencompraActual.getESTADO().equals("N")) {
			response.put("mensaje",
					"La Orden de Compra con el ID:" + id.toString() + "se encuentra anulada, no se puede despachar!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			ordencompraActual.setESTADO("I");
			ordencompraActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			ordencompraActualizada = ordencompraservice.grabarOrdencompra(ordencompraActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la orden de compra!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El ordencompra ha sido actualizada con éxito!");
		response.put("ordencompra", ordencompraActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/filtro")
	public ResponseEntity<?> filtroOrdencompras(@RequestParam(value = "subalmacen", required = false) String subalmacen,
			@RequestParam(value = "almacen", required = false) String almacen,
			@RequestParam(value = "fecha1", required = false) String fecha1,
			@RequestParam(value = "fecha2", required = false) String fecha2) {

		List<Ordencompra> filtroordencompras = null;

		Map<String, Object> response = new HashMap<>();

		if (subalmacen.equals("") && almacen.equals("") && fecha1.equals("") && fecha2.equals("")) {
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

		try {
			filtroordencompras = ordencompraservice.filtroOrdencompra(subalmacen, almacen, fecha1, fecha2);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Ordencompra>>(filtroordencompras, HttpStatus.OK);

	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/ordenesdelmes")
	public ResponseEntity<?> ordenesMesActual() {
		
		String moneda = null;
		
		List<Ordencompra> filtroordencompra = null;

		Map<String, Object> response = new HashMap<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
		
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.DAY_OF_MONTH,1);
		String back = sdf.format(calendar.getTime());
		
		Calendar ca = Calendar.getInstance();    
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
		String last = sdf.format(ca.getTime());

		try {
			filtroordencompra = ordencompraservice.filtroGastos(back,last, moneda);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Ordencompra>>(filtroordencompra, HttpStatus.OK);
	}

}
