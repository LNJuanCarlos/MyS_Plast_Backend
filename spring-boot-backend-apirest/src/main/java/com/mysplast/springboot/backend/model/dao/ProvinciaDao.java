package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mysplast.springboot.backend.model.entity.Provincia;

public interface ProvinciaDao extends CrudRepository<Provincia, String> {
	
	@Query("SELECT p FROM Provincia p WHERE p.id_departamento.id_departamento =?1 ")
	public List<Provincia> buscarPorDepartamento (String dept);

}
