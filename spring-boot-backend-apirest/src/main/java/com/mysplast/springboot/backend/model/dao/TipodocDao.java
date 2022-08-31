package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mysplast.springboot.backend.model.entity.Tipodoc;

public interface TipodocDao extends CrudRepository<Tipodoc, Long> {
	
	@Query("SELECT t FROM Tipodoc t WHERE t.ID_TIPODOC = ?1")
	public List<Tipodoc> buscarPorId(Long id);


}
