package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Transferencia;

public interface TransferenciaDao extends CrudRepository<Transferencia, String> {
	
	@Query(value="EXEC usp_consultawhtransferencia @sector = :sector, @almacen = :almacen, @fecha1 = :fecha1, @fecha2 = :fecha2", nativeQuery = true)
	public List<Transferencia> filtroWhingreso(@Param("sector") String sector, @Param("almacen") String almacen, @Param("fecha1")String fecha1,@Param("fecha2")String fecha2);
	

	public List<Transferencia> findTop50ByOrderByFECHATRANDesc();

}
