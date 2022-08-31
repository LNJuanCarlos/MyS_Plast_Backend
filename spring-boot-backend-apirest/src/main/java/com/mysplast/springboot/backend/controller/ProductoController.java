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

import com.mysplast.springboot.backend.model.entity.Producto;
import com.mysplast.springboot.backend.model.service.ProductoService;

@CrossOrigin(origins= {"http://localhost:4200"})
@Controller
@RequestMapping("/producto")
public class ProductoController {
	
	@Autowired
	private ProductoService productoservice;
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/listar")
	public ResponseEntity<?> listarProductos(){
		
		List<Producto> productos = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			productos = productoservice.listarProductos();
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable String id){
		
		Producto producto = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			producto = productoservice.buscarProductoxID(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(producto == null) {
			response.put("mensaje", "El Producto con el ID: ".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable String id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			productoservice.eliminarProductoxID(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha eliminado el Producto correctamente!");
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PostMapping("/crear")
	public ResponseEntity<?> crearProducto(@RequestBody Producto producto){
	
		Producto nuevaproducto = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		
		try {
			producto.setESTADO("A");
			producto.setREG_USER(authentication.getName());
			producto.setFECH_REG_USER(ZonedDateTime.now().toLocalDate().toString());
			nuevaproducto =	productoservice.grabarProducto(producto);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha creado correctamente el Producto!");
		response.put("producto", nuevaproducto);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN" , "ROLE_JEFE", "ROLE_LOGISTICA"})
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarProducto(@RequestBody Producto producto){
		
		Producto productoActual = productoservice.buscarProductoxID(producto.getID_PRODUCTO());
		Producto productoActualizada =  null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> response = new HashMap<>();
		

		if(productoActual == null) {
			response.put("mensaje", "El Producto con el ID: ".concat(producto.getID_PRODUCTO().toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		try {
			productoActual.setNOMBRE(producto.getNOMBRE());
			productoActual.setDESC_PRODUCTO(producto.getDESC_PRODUCTO());
			productoActual.setId_UNMEDIDA(producto.getId_UNMEDIDA());
			productoActual.setId_CATEGORIA(producto.getId_CATEGORIA());
			productoActual.setFLAG_INSUMO(producto.getFLAG_INSUMO());
			productoActual.setFLAG_PRODUCCION(producto.getFLAG_PRODUCCION());
			productoActual.setId_MARCA(producto.getId_MARCA());
			productoActual.setESTADO(producto.getESTADO());
			productoActual.setMOD_USER(authentication.getName());
			productoActual.setFECH_MOD_USER(ZonedDateTime.now().toLocalDate().toString());
			productoActualizada = productoservice.grabarProducto(productoActual);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha actualizado correctamente el Producto!");
		response.put("producto", productoActualizada);
		return new ResponseEntity<Map<String, Object> >(response, HttpStatus.OK);
		
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/filtrar-producto/{term}")
	public ResponseEntity<?> buscarProductoNombre(@PathVariable String term) {

		List<Producto> productos = null;

		Map<String, Object> response = new HashMap<>();

		if (term.equals("")) {
			response.put("mensaje", "No existe el producto con el término ingresado!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			productos = productoservice.findByNombre(term);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
	}
	
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/filtrar-productoprod/{term}")
	public ResponseEntity<?> buscarProductodeProducción(@PathVariable String term) {

		List<Producto> productos = null;

		Map<String, Object> response = new HashMap<>();

		if (term.equals("")) {
			response.put("mensaje", "No existe el producto con el término ingresado!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			productos = productoservice.buscarProductosdeProducción(term);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
	}
	
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/filtrar-productoinsm/{term}")
	public ResponseEntity<?> buscarProductodeInsumos(@PathVariable String term) {

		List<Producto> productos = null;

		Map<String, Object> response = new HashMap<>();

		if (term.equals("")) {
			response.put("mensaje", "No existe el producto con el término ingresado!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			productos = productoservice.buscarProductosdeInsumos(term);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ALMACEN", "ROLE_ADMIN", "ROLE_JEFE", "ROLE_LOGISTICA" })
	@GetMapping("/filtro")
	public ResponseEntity<?> filtroProductos(@RequestParam(value = "categoria", required = false) String categoria,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "marca", required = false) String marca) {

		List<Producto> filtroproductos = null;

		Map<String, Object> response = new HashMap<>();

		if ( categoria.equals("") && nombre.equals("") && marca.equals("")) {
			response.put("mensaje", "Tiene que ingresar al menos un dato!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (categoria.equals("")) {
			categoria = null;
		}
		if (nombre.equals("")) {
			nombre = null;
		}
		if (marca.equals("")) {
			marca = null;
		}

		try {
			filtroproductos = productoservice.filtroProductos(categoria, nombre, marca);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<Producto>>(filtroproductos, HttpStatus.OK);
	}



}
