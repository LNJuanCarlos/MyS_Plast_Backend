package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.ProvinciaDao;
import com.mysplast.springboot.backend.model.entity.Provincia;

@Service
@Transactional
public class ProvinciaService {
	
	@Autowired
	private ProvinciaDao provinciarepo;
	
	public List<Provincia> listarProvincias(){
		return (List<Provincia>) provinciarepo.findAll();
	}
	
	public List<Provincia> listarPorDepartamento(String dept){
		return (List<Provincia>) provinciarepo.buscarPorDepartamento(dept);
	}
	
	public Provincia buscarProvinciaId(String id) {
		return provinciarepo.findById(id).get();
	}

}
