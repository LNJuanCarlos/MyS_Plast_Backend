package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.UnmedidaDao;
import com.mysplast.springboot.backend.model.entity.Unmedida;

@Service
@Transactional
public class UnmedidaService {
	
	@Autowired
	private UnmedidaDao unmedidarepo;
	
	public List<Unmedida> listarUnmedidas(){
		return (List<Unmedida>) unmedidarepo.findAll();
	}
	
	public Unmedida buscarUnmedidaxId(String id) {
		return unmedidarepo.findById(id).get();
	}
	
	public Unmedida grabarUnmedida(Unmedida unmedida) {
		return unmedidarepo.save(unmedida);
		
	}
	
	public void eliminarUnmedidaxId(String id) {
		 unmedidarepo.deleteById(id);
	}

}
