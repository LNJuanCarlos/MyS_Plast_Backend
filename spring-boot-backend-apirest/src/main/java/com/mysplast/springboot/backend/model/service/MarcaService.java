package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.MarcaDao;
import com.mysplast.springboot.backend.model.entity.Marca;

@Service
@Transactional
public class MarcaService {
	
	@Autowired
	private MarcaDao marcarepo;
	
	public List<Marca> listarMarcas(){
		return (List<Marca>) marcarepo.findAll();
	}
	
	public Marca buscarMarcaxId(String id) {
		return marcarepo.findById(id).get();
	}
	
	public List<Marca> buscarMarcaxTermino(String term) {
		return marcarepo.filtrarMarcaxTermino(term);
	}
	
	public Marca grabarMarca(Marca marca) {
		return marcarepo.save(marca);
	}
	
	public void eliminarMarca(String id) {
		marcarepo.deleteById(id);
	}

}
