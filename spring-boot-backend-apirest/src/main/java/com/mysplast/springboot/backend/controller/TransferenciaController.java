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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysplast.springboot.backend.model.entity.Itemtransaccion;
import com.mysplast.springboot.backend.model.entity.Kardex;
import com.mysplast.springboot.backend.model.entity.Producto;
import com.mysplast.springboot.backend.model.entity.Sector;
import com.mysplast.springboot.backend.model.entity.Stock;
import com.mysplast.springboot.backend.model.entity.Tipotransaccion;
import com.mysplast.springboot.backend.model.entity.Transferencia;
import com.mysplast.springboot.backend.model.service.ItemtransaccionService;
import com.mysplast.springboot.backend.model.service.KardexService;
import com.mysplast.springboot.backend.model.service.StockService;
import com.mysplast.springboot.backend.model.service.Tipotransaccionservice;
import com.mysplast.springboot.backend.model.service.TransferenciaService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/transferencia")
public class TransferenciaController {
	
	@Autowired
	private TransferenciaService transferenciaservice;

	@Autowired
	private StockService productostockservice;

	@Autowired
	private Tipotransaccionservice tipotransaccionservice;
	
	@Autowired
	private ItemtransaccionService whtransaccionitemservice;
	
	@Autowired
	private KardexService kardexservice;

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarTodo() {
		
		List<Transferencia> whtransferencia = null;

		Map<String, Object> response = new HashMap<>();

		try {
			whtransferencia = transferenciaservice.listarTransferencias();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Transferencia>>(whtransferencia, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listartop")
	public ResponseEntity<?> listarTop50() {
		
		List<Transferencia> whtransferencia = null;

		Map<String, Object> response = new HashMap<>();

		try {
			whtransferencia = transferenciaservice.listarTop50Transferencia();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Transferencia>>(whtransferencia, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id) {

		Transferencia whtransferencia = null;

		Map<String, Object> response = new HashMap<>();

		try {
			whtransferencia = transferenciaservice.buscarTransferenciaId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (whtransferencia == null) {
			response.put("mensaje", "La Transferencia con el ID:".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Transferencia>(whtransferencia, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE"})
	@PostMapping("/transferencia")
	public ResponseEntity<?> crear(@RequestBody Transferencia whtransferencia) {
		boolean StockNegativo = false;
		boolean StockNulo = false;
		Transferencia nuevowhtransferencia = null;
		List<Itemtransaccion> whtransaccionitemActual = whtransferencia.getItems();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		Tipotransaccion nuevotipotransaccion = tipotransaccionservice.buscarTipotransaccionId(Long.parseLong("3"));

		for (int i = 0; i < whtransaccionitemActual.size(); i++) {

			String subalmacen = whtransferencia.getId_SECTOR().getID_SECTOR();
			String producto = whtransaccionitemActual.get(i).getId_PRODUCTO().getID_PRODUCTO();
			Stock productostockactual = productostockservice.buscarPorAlmacen(subalmacen, producto);

			if (productostockactual.equals(null)) {
				StockNulo = true;
				break;
			} else {
				double cantidad = whtransaccionitemActual.get(i).getCANTIDAD();
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
					

					whtransferencia.setESTADO("A");
					whtransferencia.setId_TIPOTRANSACCION(nuevotipotransaccion);
					whtransferencia.setREG_USER(authentication.getName());
					whtransferencia.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
					nuevowhtransferencia = transferenciaservice.grabarTransferencia(whtransferencia);

					for (int i = 0; i < whtransaccionitemActual.size(); i++) {
						
						Producto productoItem = whtransaccionitemActual.get(i).getId_PRODUCTO();
						Sector subalmItem = whtransferencia.getId_SECTOR();
						String subalmacen = whtransferencia.getId_SECTOR().getID_SECTOR();
						String subalmacendest = whtransferencia.getId_SECTORDEST().getID_SECTOR();
						Sector subalmItemdest = whtransferencia.getId_SECTORDEST();
						String producto = whtransaccionitemActual.get(i).getId_PRODUCTO().getID_PRODUCTO();
						Stock productostockactual = productostockservice.buscarPorAlmacen(subalmacen, producto);
						Stock productostockactualdest = productostockservice.buscarPorAlmacen(subalmacendest,
								producto);

						double cantidad = whtransaccionitemActual.get(i).getCANTIDAD();
						double cantidadactualizada = productostockactual.getCANTIDAD() - cantidad;
						productostockactual.setCANTIDAD(cantidadactualizada);
						productostockservice.grabarProductoStock(productostockactual);
						
						Kardex kardexactual = kardexservice.buscarPorAlmacen(subalmacen, producto);
						Kardex nuevoKardex = new Kardex();
						nuevoKardex.setId_TRAN(whtransferencia);
						nuevoKardex.setId_PRODUCTO(productoItem);
						nuevoKardex.setId_SECTOR(subalmItem);
						nuevoKardex.setFECHA(ZonedDateTime.now().toLocalDate().toString());
						nuevoKardex.setOPERACION("R");
						nuevoKardex.setCONDICION("Origen Transferencia de Mercadería");
						double stockactual = kardexactual.getSTOCKFECHA()-cantidad;
						nuevoKardex.setSTOCKFECHA(stockactual);
						nuevoKardex.setCANTIDAD(cantidad);
						kardexservice.grabarKardex(nuevoKardex);

						if (productostockactualdest == null) {

							Producto productoItemdest = whtransaccionitemActual.get(i).getId_PRODUCTO();
							Stock nuevoProductoStockdest = new Stock();
							nuevoProductoStockdest.setCANTIDAD(cantidad);
							nuevoProductoStockdest.setId_PRODUCTO(productoItemdest);
							nuevoProductoStockdest.setId_SECTOR(subalmItemdest);
							productostockservice.grabarProductoStock(nuevoProductoStockdest);

						} else {

							double cantidadactalizadadest = productostockactualdest.getCANTIDAD() + cantidad;
							productostockactualdest.setCANTIDAD(cantidadactalizadadest);
							productostockservice.grabarProductoStock(productostockactualdest);

						}
						
						Kardex kardexactualdest = kardexservice.buscarPorAlmacen(subalmacendest, producto);
						Kardex nuevoKardexdest = new Kardex();
						nuevoKardexdest.setId_TRAN(whtransferencia);
						nuevoKardexdest.setId_PRODUCTO(productoItem);
						nuevoKardexdest.setId_SECTOR(subalmItemdest);
						nuevoKardexdest.setFECHA(ZonedDateTime.now().toLocalDate().toString());
						nuevoKardexdest.setOPERACION("S");
						nuevoKardexdest.setCONDICION("Destino Transferencia de Mercadería");
						nuevoKardexdest.setCANTIDAD(cantidad);
						
						if(kardexactualdest==null) {
							nuevoKardexdest.setSTOCKFECHA(cantidad);

						} else {
							double stockactualdest = kardexactualdest.getSTOCKFECHA()+cantidad;
							nuevoKardexdest.setSTOCKFECHA(stockactualdest);
						}
						
						kardexservice.grabarKardex(nuevoKardexdest);

					}

					
				}
			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Transferencia de Producto ha sido registrada con éxito!");
		response.put("whtransferencia", nuevowhtransferencia);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE"})
	@PutMapping("/estado/{id}")
	public ResponseEntity<?> anularTransferenciaDeProducto(@RequestBody Transferencia whtransferencia,
			@PathVariable String id) {
		
		boolean StockNegativo = false;
		boolean StockNulo = false;
		Transferencia whtransferenciaActual = transferenciaservice.buscarTransferenciaId(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Itemtransaccion> whtransaccionitemActual = whtransferencia.getItems();
		Transferencia whtransferenciaActualizada = null;
		Map<String, Object> response = new HashMap<>();

		if (whtransferenciaActual == null) {
			response.put("mensaje", "El Ingreso con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (whtransferenciaActual.getESTADO().equals("N")) {
			response.put("mensaje", "La Transferencia de Mercadería con el ID:" + id.toString() + "ya está anulada!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		for (int i = 0; i < whtransaccionitemActual.size(); i++) {

			String subalmacendest = whtransferencia.getId_SECTORDEST().getID_SECTOR();
			String producto = whtransaccionitemActual.get(i).getId_PRODUCTO().getID_PRODUCTO();
			Stock productostockactualdest = productostockservice.buscarPorAlmacen(subalmacendest, producto);

			if (productostockactualdest.equals(null)) {
				StockNulo = true;
				break;
			} else {
				double cantidad = whtransaccionitemActual.get(i).getCANTIDAD();
				double cantidadactualizada = productostockactualdest.getCANTIDAD() - cantidad;
				if (cantidadactualizada < 0) {
					StockNegativo = true;
					break;
				}
			}
		}


		try {
			
			if (StockNulo == true) {
				response.put("mensaje", "No se cuenta con stock de el/los productos indicados en el almacén de destino, verificar!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
			} else {
				if (StockNegativo == true) {
					response.put("mensaje", "El stock del producto no puede quedar en negativo en el almacén de destino, verificar!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
				} else {

					for (int i = 0; i < whtransaccionitemActual.size(); i++) {

						Producto productoItem = whtransaccionitemActual.get(i).getId_PRODUCTO();
						Sector subalmItem = whtransferencia.getId_SECTOR();
						Sector subalmItemdest = whtransferencia.getId_SECTORDEST();
						String subalmacen = whtransferencia.getId_SECTOR().getID_SECTOR();
						String subalmacendest = whtransferencia.getId_SECTORDEST().getID_SECTOR();
						String producto = whtransaccionitemActual.get(i).getId_PRODUCTO().getID_PRODUCTO();
						Stock productostockactual = productostockservice.buscarPorAlmacen(subalmacen, producto);
						Stock productostockactualdest = productostockservice.buscarPorAlmacen(subalmacendest,
								producto);

						double cantidad = whtransaccionitemActual.get(i).getCANTIDAD();
						double cantidadactualizadadest = productostockactualdest.getCANTIDAD() - cantidad;
						productostockactualdest.setCANTIDAD(cantidadactualizadadest);
						productostockservice.grabarProductoStock(productostockactualdest);
						
						Itemtransaccion whtransaccionitemactualizado = whtransaccionitemActual.get(i);
						whtransaccionitemactualizado.setCANTIDAD(0);
						whtransaccionitemservice.grabarItemtransaccion(whtransaccionitemactualizado);
						
						
						Kardex kardexactual = kardexservice.buscarPorAlmacen(subalmacen, producto);
						Kardex nuevoKardex = new Kardex();
						nuevoKardex.setId_TRAN(whtransferencia);
						nuevoKardex.setId_PRODUCTO(productoItem);
						nuevoKardex.setId_SECTOR(subalmItem);
						nuevoKardex.setFECHA(ZonedDateTime.now().toLocalDate().toString());
						nuevoKardex.setOPERACION("S");
						nuevoKardex.setCONDICION("Origen Transferencia de Mercadería");
						double stockactual = kardexactual.getSTOCKFECHA()+cantidad;
						nuevoKardex.setSTOCKFECHA(stockactual);
						nuevoKardex.setCANTIDAD(cantidad);
						kardexservice.grabarKardex(nuevoKardex);

						if (productostockactualdest.equals(null)) {

							response.put("mensaje", "El producto no puede estar nulo en el almacén de origen!, verificar!");
							return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);

						} else {

							double cantidadactaliza = productostockactual.getCANTIDAD() + cantidad;
							productostockactual.setCANTIDAD(cantidadactaliza);
							productostockservice.grabarProductoStock(productostockactual);
							
							Kardex kardexactualdest = kardexservice.buscarPorAlmacen(subalmacendest, producto);
							Kardex nuevoKardexdest = new Kardex();
							nuevoKardexdest.setId_TRAN(whtransferencia);
							nuevoKardexdest.setId_PRODUCTO(productoItem);
							nuevoKardexdest.setId_SECTOR(subalmItemdest);
							nuevoKardexdest.setFECHA(ZonedDateTime.now().toLocalDate().toString());
							nuevoKardexdest.setOPERACION("R");
							nuevoKardexdest.setCANTIDAD(cantidad);
							nuevoKardexdest.setCONDICION("Destino Transferencia de Mercadería");
							
							if(kardexactualdest==null) {
								nuevoKardexdest.setSTOCKFECHA(cantidad);

							} else {
								double stockactualdest = kardexactualdest.getSTOCKFECHA()-cantidad;
								nuevoKardexdest.setSTOCKFECHA(stockactualdest);
							}
							
							kardexservice.grabarKardex(nuevoKardexdest);

						}

					}


					whtransferenciaActual.setESTADO("N");
					whtransferenciaActual.setMOD_USER(authentication.getName());
					whtransferenciaActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
					whtransferenciaActualizada = transferenciaservice.grabarTransferencia(whtransferenciaActual);

				}
			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al anular la Transferencia de Mercadería!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Se ha anulado la Transferencia de Mercadería!");
		response.put("whtransferencia", whtransferenciaActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/filtro")
	public ResponseEntity<?> filtroWhingresos(
			@RequestParam(value = "subalmacen", required = false) String subalmacen,
			@RequestParam(value = "almacen", required = false) String almacen,
			@RequestParam(value = "fecha1", required = false) String fecha1,
			@RequestParam(value = "fecha2", required = false) String fecha2) {
		
		List<Transferencia> filtrotransferencias = null;

		Map<String, Object> response = new HashMap<>();
		
		if (subalmacen.equals("") && almacen.equals("") && fecha1.equals("") && fecha2.equals("") ) {
			response.put("mensaje", "Tiene que ingresar al menos un dato!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (fecha1!="" && fecha2.equals("") || fecha1.equals("") && fecha2!="") {
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
			filtrotransferencias = transferenciaservice.filtroTransferencia(subalmacen, almacen, fecha1, fecha2);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Transferencia>>(filtrotransferencias, HttpStatus.OK);
	}


}
