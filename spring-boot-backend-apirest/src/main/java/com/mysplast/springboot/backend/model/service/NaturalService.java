package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.NaturalDao;
import com.mysplast.springboot.backend.model.entity.Natural;

@Service
@Transactional
public class NaturalService {
	
	@Autowired
	private NaturalDao naturalrepo;
	
	public List<Natural> listarNaturales(){
		return (List<Natural>) naturalrepo.findAll();
	}
	
	public Natural buscarNaturalxID(String ID) {
		return naturalrepo.findById(ID).get();
	}
	
	public Natural grabarNatural(Natural natural) {
		return naturalrepo.save(natural);
	}
	
	public void eliminarNaturalxID(String ID) {
		naturalrepo.deleteById(ID);
	}
	
	public List<Natural> filtroNatural(String nombre, String nrodoc){
		return (List<Natural>) naturalrepo.filtrarNatural(nombre, nrodoc);
	}

}
