package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.DepartamentoDao;
import com.mysplast.springboot.backend.model.entity.Departamento;

@Service
@Transactional
public class DepartamentoService {
	
	@Autowired
	private DepartamentoDao departamentorepo;
	
	public List<Departamento> listarDepartamentos(){
		return (List<Departamento>) departamentorepo.findAll();
	}
	
	public Departamento buscarDepartamentoId(String id) {
		return  departamentorepo.findById(id).get();
	}

}
