package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.OrdenprodDao;
import com.mysplast.springboot.backend.model.entity.Ordenprod;

@Service
@Transactional
public class OrdenprodService {
	
	@Autowired
	private OrdenprodDao ordenprodrepo;
	
	public List<Ordenprod> listarOrdenprod(){
		return ordenprodrepo.findTop50ByOrderByFECHADesc();
	}
	
	public Ordenprod buscarOrdenprodXId(String id) {
		return ordenprodrepo.findById(id).get();
	}
	
	public Ordenprod grabarOrdenprod(Ordenprod ordenprod) {
		return ordenprodrepo.save(ordenprod);
	}
	
	public void eliminarOrdenprodxID(String id) {
		ordenprodrepo.deleteById(id);
	}
	
	public List<Ordenprod> filtroOrdenprod(String subalmacen, String almacen, String fecha1, String fecha2, String estado){
		return (List<Ordenprod>) ordenprodrepo.filtroOrdenProd(subalmacen, almacen, fecha1, fecha2, estado);
	}

}
