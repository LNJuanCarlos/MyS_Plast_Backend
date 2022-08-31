package com.mysplast.springboot.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysplast.springboot.backend.model.entity.IngresosAlmacen;
import com.mysplast.springboot.backend.model.entity.Kardex;
import com.mysplast.springboot.backend.model.entity.Stock;
import com.mysplast.springboot.backend.model.service.IngresoService;
import com.mysplast.springboot.backend.model.service.KardexService;
import com.mysplast.springboot.backend.model.service.StockService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/reporte")
public class ReporteController {
	
	@Autowired
	private StockService productostockservice;

	@Autowired
	private KardexService kardexservice;
	
	@Autowired
	private IngresoService ingresoservice;

	@GetMapping("/filtrostock")
	public ResponseEntity<?> filtroProductos(@RequestParam(value = "subalm", required = false) String subalm,
			@RequestParam(value = "alm", required = false) String alm,
			@RequestParam(value = "prod", required = false) String prod) {
		
		List<Stock> filtroproductostock = null;

		Map<String, Object> response = new HashMap<>();
		
		if (subalm.equals("") && alm.equals("") && prod.equals("")) {
			response.put("mensaje", "Tiene que ingresar al menos un dato!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (subalm.equals("")) {
			subalm = null;
		}
		if (alm.equals("")) {
			alm = null;
		}
		if (prod.equals("")) {
			prod = null;
		}

		try {
			filtroproductostock = productostockservice.filtroStock(subalm, alm, prod);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}


		return new ResponseEntity<List<Stock>>(filtroproductostock, HttpStatus.OK);
	}

	@GetMapping("/filtrokardex")
	public ResponseEntity<?> filtroKardex(
			@RequestParam(value = "sector", required = false) String sector,
			@RequestParam(value = "almacen", required = false) String almacen,
			@RequestParam(value = "producto", required = false) String producto,
			@RequestParam(value = "fecha1", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String fecha1,
			@RequestParam(value = "fecha2", required = false) String fecha2) {
		
		List<Kardex> filtrokardex = null;

		Map<String, Object> response = new HashMap<>();
		

		if (sector.equals("") && almacen.equals("") && producto.equals("") && fecha1.equals("")
				&& fecha2.equals("")) {
			response.put("mensaje", "Tiene que ingresar al menos un dato!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (fecha1!="" && fecha2.equals("") || fecha1.equals("") && fecha2!="") {
			response.put("mensaje", "Si va a filtrar por fechas debe escoger un rango de fechas!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}


		if (sector.equals("")) {
			sector = null;
		}
		if (almacen.equals("")) {
			almacen = null;
		}
		if (producto.equals("")) {
			producto = null;
		}
		if (fecha1.equals("")) {
			fecha1 = null;
		}
		if (fecha2.equals("")) {
			fecha2 = null;
		}

		try {
			filtrokardex = kardexservice.filtroKardex(sector, almacen, producto, fecha1, fecha2);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Kardex>>(filtrokardex, HttpStatus.OK);
	}
	
	
	@GetMapping("/filtroinxalm")
	public ResponseEntity<?> filtroIngresosxAlmacen(
			@RequestParam(value = "producto", required = false) String producto,
			@RequestParam(value = "fecha1", required = false) String fecha1,
			@RequestParam(value = "fecha2", required = false) String fecha2) {
		
		List<IngresosAlmacen> filtroingresos = null;

		Map<String, Object> response = new HashMap<>();
		

		if (fecha1.equals("") || fecha2.equals("") || producto.equals("")) {
			response.put("mensaje", "Todos los campos son obligatorios!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (fecha1!="" && fecha2.equals("") || fecha1.equals("") && fecha2!="") {
			response.put("mensaje", "Si va a filtrar por fechas debe escoger un rango de fechas!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (fecha1.equals("")) {
			fecha1 = null;
		}
		if (fecha2.equals("")) {
			fecha2 = null;
		}
		if (producto.equals("")) {
			producto = null;
		}

		try {
			filtroingresos = ingresoservice.filtroIngresosxAlmacen(producto, fecha1, fecha2);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<IngresosAlmacen>>(filtroingresos, HttpStatus.OK);
	}


}
