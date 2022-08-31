package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.CategoriaDao;
import com.mysplast.springboot.backend.model.entity.Categoria;

@Service
@Transactional
public class CategoriaService {
	
	@Autowired
	private CategoriaDao categoriarepo;
	
	public List<Categoria> listarCategorias(){
		return (List<Categoria>) categoriarepo.findAll();
	}
	
	public Categoria buscarCategoriaxId(String id) {
		return categoriarepo.findById(id).get();
	}
	
	public Categoria grabarCategoria(Categoria categoria) {
		return categoriarepo.save(categoria);
	}
	
	public void eliminarCategoria(String id) {
		 categoriarepo.deleteById(id);
	}

}
