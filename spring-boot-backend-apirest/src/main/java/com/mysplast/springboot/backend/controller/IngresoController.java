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

import com.mysplast.springboot.backend.model.entity.Ingreso;
import com.mysplast.springboot.backend.model.entity.Itemtransaccion;
import com.mysplast.springboot.backend.model.entity.Kardex;
import com.mysplast.springboot.backend.model.entity.Producto;
import com.mysplast.springboot.backend.model.entity.Sector;
import com.mysplast.springboot.backend.model.entity.Stock;
import com.mysplast.springboot.backend.model.entity.Tipotransaccion;
import com.mysplast.springboot.backend.model.service.IngresoService;
import com.mysplast.springboot.backend.model.service.ItemtransaccionService;
import com.mysplast.springboot.backend.model.service.KardexService;
import com.mysplast.springboot.backend.model.service.StockService;
import com.mysplast.springboot.backend.model.service.Tipotransaccionservice;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/ingreso")
public class IngresoController {
	
	@Autowired
	private IngresoService ingresoservice;
	
	@Autowired
	private ItemtransaccionService whtransaccionitemservice;
	
	@Autowired
	private StockService productostockservice;
	
	@Autowired
	private Tipotransaccionservice tipotransaccionservice;
	
	@Autowired
	private KardexService kardexservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarTodo(){
		
		List<Ingreso> ingresos = null;

		Map<String, Object> response = new HashMap<>();

		try {
			ingresos = ingresoservice.listarIngresos();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Ingreso>>(ingresos, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listartop")
	public ResponseEntity<?> listarTop50(){
		
		List<Ingreso> ingresos = null;

		Map<String, Object> response = new HashMap<>();

		try {
			ingresos = ingresoservice.listarTop50Ingresos();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Ingreso>>(ingresos, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId (@PathVariable String id) {
		
		Ingreso ingreso = null;

		Map<String, Object> response = new HashMap<>();

		try {
			ingreso = ingresoservice.buscarIngresosId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(ingreso == null) {
			response.put("mensaje", "El Ingreso con el ID:".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<Ingreso>(ingreso, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscarnorden/{norden}")
	public ResponseEntity<?> buscarPorNroOrden(@PathVariable String norden) {
		
		Ingreso ingreso = null;

		Map<String, Object> response = new HashMap<>();

		try {
			ingreso = ingresoservice.buscarPorNroOrden(norden);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Ingreso>(ingreso, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE"})
	@PostMapping("/ingreso")
	public ResponseEntity<?> crear(@RequestBody Ingreso ingreso) {

		Ingreso nuevoingreso = null;
		List<Itemtransaccion> whtransaccionitemActual = ingreso.getItems();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		Tipotransaccion nuevotipotransaccion = tipotransaccionservice.buscarTipotransaccionId(Long.parseLong("1"));
		
	
		
		try {
			if(ingreso.getNRO_ORDEN()!="") {
				ingreso.setESTADO("I");
				ingreso.setFECHATRAN(ZonedDateTime.now().toLocalDate().toString());
			} else {
				ingreso.setESTADO("A");
			}
			ingreso.setREG_USER(authentication.getName());
			ingreso.setId_TIPOTRANSACCION(nuevotipotransaccion);
			ingreso.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
			nuevoingreso = ingresoservice.grabarIngreso(ingreso);
			
			for(int i=0;i<whtransaccionitemActual.size();i++) {
				double cantidad = whtransaccionitemActual.get(i).getCANTIDAD();
				String subalmacen = ingreso.getId_SECTOR().getID_SECTOR();
				String producto = whtransaccionitemActual.get(i).getId_PRODUCTO().getID_PRODUCTO();
				Producto productoItem = whtransaccionitemActual.get(i).getId_PRODUCTO();
				Sector subalmItem = ingreso.getId_SECTOR();
				Stock productostockactual = productostockservice.buscarPorAlmacen(subalmacen, producto);
				Kardex kardexactual = kardexservice.buscarPorAlmacen(subalmacen, producto);
				
				if(productostockactual==null) {
					
					Stock nuevoProductoStock = new Stock();
					nuevoProductoStock.setCANTIDAD(cantidad);
					nuevoProductoStock.setId_PRODUCTO(productoItem);
					nuevoProductoStock.setId_SECTOR(subalmItem);
					productostockservice.grabarProductoStock(nuevoProductoStock);
					
				} else {
					
					double cantidadactualizada =  productostockactual.getCANTIDAD() + cantidad;
					productostockactual.setCANTIDAD(cantidadactualizada);
					productostockservice.grabarProductoStock(productostockactual);
				}
				
				Kardex nuevoKardex = new Kardex();
				nuevoKardex.setId_TRAN(ingreso);
				nuevoKardex.setId_PRODUCTO(productoItem);
				nuevoKardex.setId_SECTOR(subalmItem);
				nuevoKardex.setFECHA(ZonedDateTime.now().toLocalDate().toString());
				nuevoKardex.setOPERACION("S");
				nuevoKardex.setCONDICION("Ingreso de Mercadería");
				nuevoKardex.setCANTIDAD(cantidad);
				
				if(kardexactual==null) {
					nuevoKardex.setSTOCKFECHA(cantidad);

				} else {
					double stockactual = kardexactual.getSTOCKFECHA()+cantidad;
					nuevoKardex.setSTOCKFECHA(stockactual);
				}
				
				kardexservice.grabarKardex(nuevoKardex);
			}
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El Ingreso de Producto ha sido registrado con éxito!");
		response.put("ingreso", nuevoingreso);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE"})
	@PutMapping("/estado/{id}")
	public ResponseEntity<?> anularIngresoDeProducto(@RequestBody Ingreso ingreso, @PathVariable String id) {
		
		boolean StockNegativo = false;
		boolean StockNulo = false;
		Ingreso ingresoActual = ingresoservice.buscarIngresosId(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Ingreso ingresoActualizada = null;
		Map<String, Object> response = new HashMap<>();
		
		if(ingresoActual == null) {
			response.put("mensaje", "El Ingreso con el ID:"+id.toString()+"no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if(ingresoActual.getESTADO().equals("N")) {
			response.put("mensaje", "El Ingreso con el ID:"+id.toString()+"ya está anulado!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		List<Itemtransaccion> whtransaccionitemActual = ingresoActual.getItems();
		List<Itemtransaccion> itemtransaccionActual = ingreso.getItems();
		
		for (int i = 0; i < itemtransaccionActual.size(); i++) {

			String sector = ingreso.getId_SECTOR().getID_SECTOR();
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
			
			for(int i=0;i<whtransaccionitemActual.size();i++) {
				
				double cantidad = whtransaccionitemActual.get(i).getCANTIDAD();
				Producto productoItem = whtransaccionitemActual.get(i).getId_PRODUCTO();
				Sector subalmItem = ingreso.getId_SECTOR();
				String subalmacen = ingresoActual.getId_SECTOR().getID_SECTOR();
				String producto = whtransaccionitemActual.get(i).getId_PRODUCTO().getID_PRODUCTO();
				Stock productostockactual = productostockservice.buscarPorAlmacen(subalmacen, producto);
				double cantidadactualizada =  productostockactual.getCANTIDAD() - whtransaccionitemActual.get(i).getCANTIDAD();
				productostockactual.setCANTIDAD(cantidadactualizada);
				productostockservice.grabarProductoStock(productostockactual);
				Itemtransaccion whtransaccionitemactualizado = whtransaccionitemActual.get(i);
				whtransaccionitemactualizado.setCANTIDAD(0);
				whtransaccionitemservice.grabarItemtransaccion(whtransaccionitemactualizado);
				
				Kardex kardexactual = kardexservice.buscarPorAlmacen(subalmacen, producto);
				Kardex nuevoKardex = new Kardex();
				nuevoKardex.setId_TRAN(ingreso);
				nuevoKardex.setId_PRODUCTO(productoItem);
				nuevoKardex.setId_SECTOR(subalmItem);
				nuevoKardex.setFECHA(ZonedDateTime.now().toLocalDate().toString());
				nuevoKardex.setOPERACION("R");
				nuevoKardex.setCONDICION("Anulación de Ingreso de Mercadería");
				double stockactual = kardexactual.getSTOCKFECHA()-cantidad;
				nuevoKardex.setSTOCKFECHA(stockactual);
				nuevoKardex.setCANTIDAD(cantidad);
				kardexservice.grabarKardex(nuevoKardex);
			}
			
			ingresoActual.setESTADO("N");
			ingresoActual.setMOD_USER(authentication.getName());
			ingresoActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			ingresoActualizada = ingresoservice.grabarIngreso(ingresoActual);
			

				}
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al anular el Ingreso de Mercadería!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha anulado el Ingreso de Mercadería!");
		response.put("ingreso", ingresoActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/filtro")
	public  ResponseEntity<?> filtroIngresos(
			@RequestParam(value = "subalmacen", required = false) String subalmacen,
			@RequestParam(value = "almacen", required = false) String almacen,
			@RequestParam(value = "fecha1", required = false) String fecha1,
			@RequestParam(value = "fecha2", required = false) String fecha2) {
		
		List<Ingreso> filtroingresos = null;

		Map<String, Object> response = new HashMap<>();
		
		if (subalmacen.equals("") && almacen.equals("") && fecha1.equals("") && fecha2.equals("") ) {
			response.put("mensaje", "Tiene que ingresar al menos un dato!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (fecha1!="" && fecha2.equals("") || fecha1.equals("") && fecha2!="") {
			response.put("mensaje", "Si va a filtrar por fechas debe escoger un rango de fechas!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if(subalmacen.equals("")) {
			subalmacen = null;
		}
		if(almacen.equals("")) {
			almacen = null;
		}
		if(fecha1.equals("")) {
			fecha1 = null;
		}
		if(fecha2.equals("")) {
			fecha2 = null;
		}
		
		try {
			filtroingresos =  ingresoservice.filtroIngreso(subalmacen, almacen, fecha1, fecha2);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Ingreso>>(filtroingresos, HttpStatus.OK);
	

	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/ingresosmesactual")
	public  ResponseEntity<?> ingresosMesActual() {
		
		String subalmacen = null;

		String almacen = null;
		
		List<Ingreso> filtroingresos = null;

		Map<String, Object> response = new HashMap<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String back = sdf.format(calendar.getTime());

		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = sdf.format(ca.getTime());
		
		try {
			filtroingresos =  ingresoservice.filtroIngreso(subalmacen, almacen, back, last);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Ingreso>>(filtroingresos, HttpStatus.OK);
	

	}

}
