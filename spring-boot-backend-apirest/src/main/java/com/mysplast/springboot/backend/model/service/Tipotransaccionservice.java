package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.TipotransaccionDao;
import com.mysplast.springboot.backend.model.entity.Tipotransaccion;

@Service
@Transactional
public class Tipotransaccionservice {
	
	@Autowired
	private TipotransaccionDao tipotransrepo;
	
	public List<Tipotransaccion> listarTipotransacciones(){
		return (List<Tipotransaccion>) tipotransrepo.findAll();
	}
	
	public Tipotransaccion buscarTipotransaccionId(Long id) {
		return tipotransrepo.findById(id).get();
	}
	
	public Tipotransaccion grabarTipotransaccion(Tipotransaccion tipotransaccion) {
		return tipotransrepo.save(tipotransaccion);
	}
	
	public void eliminarTipotransaccion(Long id) {
		tipotransrepo.deleteById(id);
	}

}
