package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Ordenprod;

public interface OrdenprodDao extends CrudRepository<Ordenprod, String> {
	
	@Query(value="EXEC usp_consultaordenprod @sector = :sector, @almacen = :almacen, @fecha1 = :fecha1, @fecha2 = :fecha2, @estado = :estado", nativeQuery = true)
	public List<Ordenprod> filtroOrdenProd(@Param("sector") String sector, @Param("almacen") String almacen, @Param("fecha1")String fecha1,@Param("fecha2")String fecha2,@Param("estado")String estado);

	public List<Ordenprod> findTop50ByOrderByFECHADesc();


}
