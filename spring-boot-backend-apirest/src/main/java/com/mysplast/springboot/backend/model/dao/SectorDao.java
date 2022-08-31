package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mysplast.springboot.backend.model.entity.Sector;

public interface SectorDao extends CrudRepository<Sector, String> {
	
	@Query("SELECT sb FROM Sector sb WHERE sb.id_ALMACEN.ID_ALMACEN = ?1")
	public List<Sector> buscarPorAlmacen(String alm);

}
