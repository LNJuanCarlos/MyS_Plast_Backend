package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mysplast.springboot.backend.model.entity.Marca;

public interface MarcaDao extends CrudRepository<Marca, String> {
	
	@Query("SELECT m FROM Marca m WHERE NOM_MARCA like %:term%")
	public List<Marca> filtrarMarcaxTermino(String term);

}
