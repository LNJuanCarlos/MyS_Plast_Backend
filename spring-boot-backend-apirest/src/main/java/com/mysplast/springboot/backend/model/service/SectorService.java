package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.SectorDao;
import com.mysplast.springboot.backend.model.entity.Sector;

@Service
@Transactional
public class SectorService {
	
	@Autowired
	private SectorDao sectorrepo;
	
	public List<Sector> listarSectores(){
		return (List<Sector>) sectorrepo.findAll();
	}
	
	public Sector buscarSectorxID(String ID) {
		return sectorrepo.findById(ID).get();
	}
	
	public Sector grabarSector(Sector sector) {
		return sectorrepo.save(sector);
	}
	
	public void eliminarSectorxID(String ID) {
		sectorrepo.deleteById(ID);
	}
	
	public List<Sector> buscarPorAlmacen(String alm){
		return (List<Sector>) sectorrepo.buscarPorAlmacen(alm);
	}
	

}
