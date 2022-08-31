package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.ActividadDao;
import com.mysplast.springboot.backend.model.entity.Actividad;

@Service
@Transactional
public class ActividadService {

	@Autowired
	private ActividadDao actividadrepo;
	
	public List<Actividad> listaActividades(){
		return (List<Actividad>) actividadrepo.findAll();
	}
	
	public Actividad buscarActividadxId(Long Id) {
		return actividadrepo.findById(Id).get();
	}
	
	public Actividad grabarActividad(Actividad actividad) {
		return actividadrepo.save(actividad);
	}
	
	public void eliminarActividad(Long Id) {
		actividadrepo.deleteById(Id);
	}
	
}
