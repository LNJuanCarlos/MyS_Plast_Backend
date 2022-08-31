package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.AlmacenDao;
import com.mysplast.springboot.backend.model.entity.Almacen;

@Service
@Transactional
public class AlmacenService {
	
	@Autowired
	private AlmacenDao almacenrepo;
	
	public List<Almacen> listarAlmacenes(){
		return (List<Almacen>) almacenrepo.findAll();
	}
	
	public Almacen buscarAlmacenxId(String Id) {
		return almacenrepo.findById(Id).get();
	}
	
	public Almacen crearAlmacen(Almacen almacen) {
		return almacenrepo.save(almacen);
	}
	
	public void eliminarAlmacenxId(String id) {
		almacenrepo.deleteById(id);
	}

}
