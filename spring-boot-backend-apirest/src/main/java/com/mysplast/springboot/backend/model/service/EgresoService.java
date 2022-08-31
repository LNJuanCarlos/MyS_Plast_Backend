package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.EgresoDao;
import com.mysplast.springboot.backend.model.entity.Egreso;

@Service
@Transactional
public class EgresoService {
	
	@Autowired
	private EgresoDao egresorepo;
	
	public List<Egreso> listarEgresos(){
		return (List<Egreso>) egresorepo.findAll();
	}
	
	public List<Egreso> listarTop50Egresos(){
		return (List<Egreso>) egresorepo.findTop50ByOrderByFECHATRANDesc();
	}
	
	public Egreso buscarEgresoId(String id){
		return egresorepo.findById(id).get();
	}
	
	public Egreso grabarEgreso(Egreso egreso) {
		return egresorepo.save(egreso);
	}
	
	public void eliminarEgreso(String id) {
		egresorepo.deleteById(id);
	}
	
	public List<Egreso> filtroEgreso(String subalmacen, String almacen, String fecha1, String fecha2){
		return (List<Egreso>) egresorepo.filtroegresos(subalmacen, almacen, fecha1, fecha2);
	}

}
