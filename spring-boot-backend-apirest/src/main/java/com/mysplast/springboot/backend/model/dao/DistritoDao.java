package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mysplast.springboot.backend.model.entity.Distrito;

public interface DistritoDao extends CrudRepository<Distrito, String> {
	
	@Query("SELECT d FROM Distrito d WHERE d.id_provincia.id_provincia = ?1")
	public List<Distrito> buscarPorProvincia(String prov);

}
