package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.ProductoDao;
import com.mysplast.springboot.backend.model.entity.Producto;

@Service
@Transactional
public class ProductoService {
	
	@Autowired
	private ProductoDao productorepo;
	
	public List<Producto> listarProductos(){
		return (List<Producto>) productorepo.findAll();
	}
	
	public Producto buscarProductoxID(String ID) {
		return productorepo.findById(ID).get();
	}
	
	public Producto grabarProducto(Producto producto) {
		return productorepo.save(producto);
	}
	
	public void  eliminarProductoxID(String ID) {
		productorepo.deleteById(ID);
	}
	
	public List<Producto> filtroProductos(String categoria, String nombre, String marca){
		return (List<Producto>) productorepo.filtrarProductos(categoria, nombre, marca);
	}
	
	public List<Producto> findByNombre(String term){
		return productorepo.findByNOMBREContainingIgnoreCase(term);
	}
	
	public List<Producto> buscarProductosdeProducción(String term){
		return productorepo.buscarProductosDeProduccion(term);
	}
	
	public List<Producto> buscarProductosdeInsumos(String term){
		return productorepo.buscarProductosDeInsumos(term);
	}

}
