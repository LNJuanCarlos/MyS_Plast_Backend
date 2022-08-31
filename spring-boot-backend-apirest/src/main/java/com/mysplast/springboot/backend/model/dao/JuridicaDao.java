package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Juridica;

public interface JuridicaDao extends CrudRepository<Juridica, String> {
	
	@Query(value="EXEC usp_consultajuridica @nrodoc = :nrodoc, @razsoc = :razsoc", nativeQuery = true)
	public List<Juridica>filtrarJuridica(@Param("nrodoc") String nrodoc, @Param("razsoc") String razsoc);

}
