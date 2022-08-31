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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysplast.springboot.backend.model.entity.Egreso;
import com.mysplast.springboot.backend.model.entity.Itemtransaccion;
import com.mysplast.springboot.backend.model.entity.Kardex;
import com.mysplast.springboot.backend.model.entity.Producto;
import com.mysplast.springboot.backend.model.entity.Sector;
import com.mysplast.springboot.backend.model.entity.Stock;
import com.mysplast.springboot.backend.model.entity.Tipotransaccion;
import com.mysplast.springboot.backend.model.service.EgresoService;
import com.mysplast.springboot.backend.model.service.ItemtransaccionService;
import com.mysplast.springboot.backend.model.service.KardexService;
import com.mysplast.springboot.backend.model.service.StockService;
import com.mysplast.springboot.backend.model.service.Tipotransaccionservice;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/egreso")
public class EgresoController {
	
	@Autowired
	private EgresoService egresoservice;

	@Autowired
	private ItemtransaccionService itemtransaccionservice;

	@Autowired
	private StockService productostockservice;

	@Autowired
	private Tipotransaccionservice tipotransaccionservice;

	@Autowired
	private KardexService kardexservice;

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/listar")
	public ResponseEntity<?> listarTodo() {

		List<Egreso> egreso = null;

		Map<String, Object> response = new HashMap<>();

		try {
			egreso = egresoservice.listarEgresos();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Egreso>>(egreso, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/listartop")
	public ResponseEntity<?> listarTop50() {

		List<Egreso> egreso = null;

		Map<String, Object> response = new HashMap<>();

		try {
			egreso = egresoservice.listarTop50Egresos();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Egreso>>(egreso, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id) {

		Egreso egreso = null;

		Map<String, Object> response = new HashMap<>();

		try {
			egreso = egresoservice.buscarEgresoId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (egreso == null) {
			response.put("mensaje", "La Salida con el ID:".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Egreso>(egreso, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE" })
	@PostMapping("/egreso")
	public ResponseEntity<?> crear(@RequestBody Egreso egreso) {

		boolean StockNegativo = false;
		boolean StockNulo = false;
		Egreso nuevoegreso = null;
		List<Itemtransaccion> itemtransaccionActual = egreso.getItems();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		Tipotransaccion nuevotipotransaccion = tipotransaccionservice.buscarTipotransaccionId(Long.parseLong("2"));

		for (int i = 0; i < itemtransaccionActual.size(); i++) {

			String sector = egreso.getId_SECTOR().getID_SECTOR();
			String producto = itemtransaccionActual.get(i).getId_PRODUCTO().getID_PRODUCTO();
			Stock productostockactual = productostockservice.buscarPorAlmacen(sector, producto);

			if (productostockactual.equals(null)) {
				StockNulo = true;
				break;
			} else {
				double cantidad = itemtransaccionActual.get(i).getCANTIDAD();
				double cantidadactualizada = productostockactual.getCANTIDAD() - cantidad;
				if (cantidadactualizada < 0) {
					StockNegativo = true;
					break;
				}
			}
		}

		try {

			if (StockNulo == true) {
				response.put("mensaje", "No se cuenta con stock de el/los productos indicados, verificar!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
			} else {
				if (StockNegativo == true) {
					response.put("mensaje", "El stock del producto no puede quedar en negativo, verificar!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
				} else {
					
					if(egreso.getNRO_ORDEN()!="") {
						egreso.setESTADO("I");
						egreso.setFECHATRAN(ZonedDateTime.now().toLocalDate().toString());
					} else {
						egreso.setESTADO("A");
					}
					egreso.setREG_USER(authentication.getName());
					egreso.setId_TIPOTRANSACCION(nuevotipotransaccion);
					egreso.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
					nuevoegreso = egresoservice.grabarEgreso(egreso);

					for (int i = 0; i < itemtransaccionActual.size(); i++) {

						String sector = egreso.getId_SECTOR().getID_SECTOR();
						String producto = itemtransaccionActual.get(i).getId_PRODUCTO().getID_PRODUCTO();
						Producto productoItem = itemtransaccionActual.get(i).getId_PRODUCTO();
						Sector subalmItem = egreso.getId_SECTOR();
						Stock productostockactual = productostockservice.buscarPorAlmacen(sector, producto);
						double cantidad = itemtransaccionActual.get(i).getCANTIDAD();
						double cantidadactualizada = productostockactual.getCANTIDAD() - cantidad;
						productostockactual.setCANTIDAD(cantidadactualizada);
						productostockservice.grabarProductoStock(productostockactual);
						Kardex kardexactual = kardexservice.buscarPorAlmacen(sector, producto);

						Kardex nuevoKardex = new Kardex();
						nuevoKardex.setId_TRAN(egreso);
						nuevoKardex.setId_PRODUCTO(productoItem);
						nuevoKardex.setId_SECTOR(subalmItem);
						nuevoKardex.setFECHA(ZonedDateTime.now().toLocalDate().toString());
						nuevoKardex.setOPERACION("R");
						nuevoKardex.setCONDICION("Salida de Mercadería");
						nuevoKardex.setCANTIDAD(cantidad);

						if (kardexactual == null) {
							nuevoKardex.setSTOCKFECHA(cantidad);

						} else {
							double stockactual = kardexactual.getSTOCKFECHA() - cantidad;
							nuevoKardex.setSTOCKFECHA(stockactual);
						}

						kardexservice.grabarKardex(nuevoKardex);

					}

				}
			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Salida de Producto ha sido registrada con éxito!");
		response.put("egreso", nuevoegreso);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE" })
	@PutMapping("/estado/{id}")
	public ResponseEntity<?> anularSalidaDeProducto(@RequestBody Egreso egreso, @PathVariable String id) {

		Egreso egresoActual = egresoservice.buscarEgresoId(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Egreso egresoActualizada = null;
		Map<String, Object> response = new HashMap<>();

		if (egresoActual == null) {
			response.put("mensaje", "El Ingreso con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (egresoActual.getESTADO().equals("N")) {
			response.put("mensaje", "La Salida de Mercadería con el ID:" + id.toString() + "ya está anulada!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		List<Itemtransaccion> itemtransaccionActual = egresoActual.getItems();

		try {

			for (int i = 0; i < itemtransaccionActual.size(); i++) {

				double cantidad = itemtransaccionActual.get(i).getCANTIDAD();
				Producto productoItem = itemtransaccionActual.get(i).getId_PRODUCTO();
				Sector subalmItem = egreso.getId_SECTOR();
				String sector = egresoActual.getId_SECTOR().getID_SECTOR();
				String producto = itemtransaccionActual.get(i).getId_PRODUCTO().getID_PRODUCTO();
				Stock productostockactual = productostockservice.buscarPorAlmacen(sector, producto);
				double cantidadactualizada = productostockactual.getCANTIDAD()
						+ itemtransaccionActual.get(i).getCANTIDAD();
				productostockactual.setCANTIDAD(cantidadactualizada);
				productostockservice.grabarProductoStock(productostockactual);
				Itemtransaccion itemtransaccionactualizado = itemtransaccionActual.get(i);
				itemtransaccionactualizado.setCANTIDAD(0);
				itemtransaccionservice.grabarItemtransaccion(itemtransaccionactualizado);

				Kardex kardexactual = kardexservice.buscarPorAlmacen(sector, producto);
				Kardex nuevoKardex = new Kardex();
				nuevoKardex.setId_TRAN(egreso);
				nuevoKardex.setId_PRODUCTO(productoItem);
				nuevoKardex.setId_SECTOR(subalmItem);
				nuevoKardex.setFECHA(ZonedDateTime.now().toLocalDate().toString());
				nuevoKardex.setOPERACION("S");
				nuevoKardex.setCANTIDAD(cantidad);
				nuevoKardex.setCONDICION("Anulación de Salida de Mercadería");
				double stockactual = kardexactual.getSTOCKFECHA() + cantidad;
				nuevoKardex.setSTOCKFECHA(stockactual);
				kardexservice.grabarKardex(nuevoKardex);
			}

			egresoActual.setESTADO("N");
			egreso.setMOD_USER(authentication.getName());
			egresoActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			egresoActualizada = egresoservice.grabarEgreso(egresoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al anular la Salida de Mercadería!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Se ha anulado la Salida de Mercadería!");
		response.put("egreso", egresoActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/filtro")
	public ResponseEntity<?> filtroWhingresos(@RequestParam(value = "sector", required = false) String sector,
			@RequestParam(value = "almacen", required = false) String almacen,
			@RequestParam(value = "fecha1", required = false) String fecha1,
			@RequestParam(value = "fecha2", required = false) String fecha2) {

		List<Egreso> filtroegresos = null;

		Map<String, Object> response = new HashMap<>();

		if (sector.equals("") && almacen.equals("") && fecha1.equals("") && fecha2.equals("")) {
			response.put("mensaje", "Tiene que ingresar al menos un dato!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (fecha1 != "" && fecha2.equals("") || fecha1.equals("") && fecha2 != "") {
			response.put("mensaje", "Si va a filtrar por fechas debe escoger un rango de fechas!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (sector.equals("")) {
			sector = null;
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
			filtroegresos = egresoservice.filtroEgreso(sector, almacen, fecha1, fecha2);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Egreso>>(filtroegresos, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/salidasmesactual")
	public ResponseEntity<?> salidasMesActual() {

		String sector = null;

		String almacen = null;

		List<Egreso> filtroegresos = null;

		Map<String, Object> response = new HashMap<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String back = sdf.format(calendar.getTime());

		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = sdf.format(ca.getTime());

		try {
			filtroegresos = egresoservice.filtroEgreso(sector, almacen, back, last);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Egreso>>(filtroegresos, HttpStatus.OK);
	}

}
