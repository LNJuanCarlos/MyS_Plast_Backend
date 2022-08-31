package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Egreso;

public interface EgresoDao extends CrudRepository<Egreso, String> {
	
	@Query(value="EXEC usp_consultaegreso @sector = :sector, @almacen = :almacen, @fecha1 = :fecha1, @fecha2 = :fecha2", nativeQuery = true)
	public List<Egreso> filtroegresos(@Param("sector") String sector, @Param("almacen") String almacen, @Param("fecha1")String fecha1,@Param("fecha2")String fecha2);
	

	public List<Egreso> findTop50ByOrderByFECHATRANDesc();

}
