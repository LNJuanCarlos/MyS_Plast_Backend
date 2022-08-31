package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.TipodocDao;
import com.mysplast.springboot.backend.model.entity.Tipodoc;

@Service
@Transactional
public class TipodocService {

	
	@Autowired
	private TipodocDao tipodocrepo;
	
	public List<Tipodoc> listarTipoDocs(){
		return (List<Tipodoc>) tipodocrepo.findAll();
	}
	
	public Tipodoc buscarTipoDocxId(Long id) {
		return tipodocrepo.findById(id).get();
	}
	
	public List<Tipodoc> buscarTipoDoscxId(Long id){
		return (List<Tipodoc>) tipodocrepo.buscarPorId(id);
	}
		
	public Tipodoc grabarTipoDoc(Tipodoc tipodoc) {
		return tipodocrepo.save(tipodoc);
	}
	
	public void eliminarTipoDocxId(Long id) {
		tipodocrepo.deleteById(id);
	}
}
