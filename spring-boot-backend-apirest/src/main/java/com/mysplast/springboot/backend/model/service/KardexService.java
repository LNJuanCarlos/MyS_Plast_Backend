package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.KardexDao;
import com.mysplast.springboot.backend.model.entity.Kardex;

@Service
@Transactional
public class KardexService {
	
	@Autowired
	private KardexDao kardexrepo;
	
	public List<Kardex> listarKardex(){
		return (List<Kardex>) kardexrepo.findAll();
	}
	
	public Kardex buscarPorAlmacen(String subalm, String prod){
		return  kardexrepo.findTop1Kardex(subalm, prod);
	}
	
	public Kardex buscarKardexId(Long id) {
		return kardexrepo.findById(id).get();
	}
	
	public Kardex grabarKardex(Kardex kardex) {
		return kardexrepo.save(kardex);
	}
	
	public void eliminarKardex(Long id) {
		kardexrepo.deleteById(id);
	}
	
	public List<Kardex> filtroKardex(String sector, String almacen, String producto, String fecha1, String fecha2){
		return (List<Kardex>) kardexrepo.filtroKardex(sector, almacen, producto, fecha1, fecha2);
	}

}
