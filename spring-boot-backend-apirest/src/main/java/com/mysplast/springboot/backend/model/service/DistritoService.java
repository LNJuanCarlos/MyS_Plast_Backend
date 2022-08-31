package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.DistritoDao;
import com.mysplast.springboot.backend.model.entity.Distrito;

@Service
@Transactional
public class DistritoService {
	
	@Autowired
	private DistritoDao distritorepo;
	
	public List<Distrito> listarDistritos(){
		return (List<Distrito>) distritorepo.findAll();
	}
	
	public List<Distrito> buscarPorProvincia(String prov){
		return (List<Distrito>) distritorepo.buscarPorProvincia(prov);
	}
	
	public Distrito buscarDistritoId(String id) {
		return distritorepo.findById(id).get();
	}

}
