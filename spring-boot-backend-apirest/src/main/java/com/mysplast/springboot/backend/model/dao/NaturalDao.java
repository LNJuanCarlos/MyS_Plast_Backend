package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Natural;

public interface NaturalDao extends CrudRepository<Natural, String> {
	
	
	@Query(value="EXEC usp_consultanatural @nombre = :nombre, @nrodoc = :nrodoc", nativeQuery = true)
	public List<Natural>filtrarNatural(@Param("nombre") String nombre, @Param("nrodoc") String nrodoc);


}
