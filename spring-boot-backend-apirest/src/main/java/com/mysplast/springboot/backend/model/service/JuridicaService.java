package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.JuridicaDao;
import com.mysplast.springboot.backend.model.entity.Juridica;

@Service
@Transactional
public class JuridicaService {
	
	@Autowired
	private JuridicaDao juridicarepo;
	
	public List<Juridica> listarJuridicas(){
		return (List<Juridica>) juridicarepo.findAll();
	}
	
	public Juridica buscarJuridicaxID(String ID) {
		return juridicarepo.findById(ID).get();
	}
	
	public Juridica grabarJuridica(Juridica juridica) {
		return juridicarepo.save(juridica);
	}
	
	public void eliminarJuridicaxID(String ID) {
		juridicarepo.deleteById(ID);
	}
	
	public List<Juridica> filtroJuridica(String nrodoc, String razsoc){
		return (List<Juridica>) juridicarepo.filtrarJuridica(nrodoc, razsoc);
	}

}
