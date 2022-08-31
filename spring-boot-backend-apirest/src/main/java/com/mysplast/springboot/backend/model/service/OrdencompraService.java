package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.OrdencompraDao;
import com.mysplast.springboot.backend.model.entity.Ordencompra;

@Service
@Transactional
public class OrdencompraService {
	
	@Autowired
	private OrdencompraDao ordencomprarepo;
	
	public List<Ordencompra> listarOrdencompras(){
		return (List<Ordencompra>) ordencomprarepo.findAll();
	}
	
	public List<Ordencompra> listarTop50Ordencompras(){
		return (List<Ordencompra>) ordencomprarepo.findTop50ByOrderByFECHADesc();
	}
	
	public Ordencompra buscarOrdencomprasId(String id){
		return ordencomprarepo.findById(id).get();
	}
	
	public Ordencompra grabarOrdencompra(Ordencompra ordencompra) {
		return ordencomprarepo.save(ordencompra);
	}
	
	public void eliminarOrdencompra(String id) {
		ordencomprarepo.deleteById(id);
	}
	
	public List<Ordencompra> filtroOrdencompra(String subalmacen, String almacen, String fecha1, String fecha2){
		return (List<Ordencompra>) ordencomprarepo.filtroOrdenCompra(subalmacen, almacen, fecha1, fecha2);
	}
	
	public List<Ordencompra> filtroGastos(String fecha1, String fecha2, String moneda){
		return (List<Ordencompra>) ordencomprarepo.filtroGastos(fecha1, fecha2, moneda);
	}
	
	public Ordencompra buscarPorNroOrden(String nroorden){
		return ordencomprarepo.buscarPorNroOrden(nroorden);
	}


}
