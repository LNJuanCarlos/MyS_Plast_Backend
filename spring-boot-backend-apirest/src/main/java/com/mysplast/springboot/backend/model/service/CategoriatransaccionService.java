package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.CategoriatransaccionDao;
import com.mysplast.springboot.backend.model.entity.Categoriatransaccion;

@Service
@Transactional
public class CategoriatransaccionService {
	
	@Autowired
	private CategoriatransaccionDao categoriatransaccionrepo;
	
	public List<Categoriatransaccion> buscarPorTipoTransaccion(Long tipotrans){
		return (List<Categoriatransaccion>) categoriatransaccionrepo.buscarPorTipoTransaccion(tipotrans);
	}
	
	public Categoriatransaccion buscarCaterogiraTransaccion(Long id) {
		return categoriatransaccionrepo.findById(id).get();
	}

}
