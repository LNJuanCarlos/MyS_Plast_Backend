package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.CentroCostoDao;
import com.mysplast.springboot.backend.model.entity.Centrocosto;

@Service
@Transactional
public class CentrocostoService {
	
	@Autowired
	private CentroCostoDao centrocostorepo;
	
	public List<Centrocosto> listarCentroCostos(){
		return (List<Centrocosto>) centrocostorepo.findAll();
	}
	
	public Centrocosto buscarCentroCostoxId(Long id) {
		return centrocostorepo.findById(id).get();
	}
	
	public Centrocosto grabarCentroCosto(Centrocosto centrocosto) {
		return centrocostorepo.save(centrocosto);
	}
	
	public void eliminarCentroCostoxId(Long id) {
		centrocostorepo.deleteById(id);
	}

}
