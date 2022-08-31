package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mysplast.springboot.backend.model.entity.Categoriatransaccion;

public interface CategoriatransaccionDao extends CrudRepository<Categoriatransaccion, Long> {
	
	@Query("SELECT ct FROM Categoriatransaccion ct WHERE ct.tipotransaccion.ID = ?1")
	public List<Categoriatransaccion> buscarPorTipoTransaccion(Long tipotrans);
	

}
