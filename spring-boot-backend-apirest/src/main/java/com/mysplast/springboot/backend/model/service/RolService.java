package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.RolDao;
import com.mysplast.springboot.backend.model.entity.Rol;

@Service
@Transactional
public class RolService {
	
	@Autowired
	private RolDao rolrepo;
	
	public List<Rol> listarTodo(){
		return (List<Rol>) rolrepo.findAll();
	}
	
	public Rol buscarRolId(Long id) {
		return rolrepo.findById(id).get();
	}

	
}