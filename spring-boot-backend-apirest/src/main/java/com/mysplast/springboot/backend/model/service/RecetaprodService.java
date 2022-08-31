package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.RecetaprodDao;
import com.mysplast.springboot.backend.model.entity.Recetaprod;

@Service
@Transactional
public class RecetaprodService {
	
	@Autowired
	private RecetaprodDao recetarepo;
	
	public List<Recetaprod> listarRecetaprods(){
		return (List<Recetaprod>) recetarepo.findAll();
	}
	
	public Recetaprod buscarRecetaprodxID(String ID) {
		return recetarepo.findById(ID).get();
	}
	
	public Recetaprod grabarRecetaprod(Recetaprod recetaprod) {
		return recetarepo.save(recetaprod);
	}
	
	public void  eliminarRecetaprodxID(String ID) {
		recetarepo.deleteById(ID);
	}
	
	public List<Recetaprod> filtroRecetaprods(String nroreceta, String producto, String nomreceta){
		return (List<Recetaprod>) recetarepo.filtrarRecetas(nroreceta, producto, nomreceta);
	}
	
	public Recetaprod buscarRecetaxProducto(String producto){
		return recetarepo.buscarRecetaxProducto(producto);
	}

}
