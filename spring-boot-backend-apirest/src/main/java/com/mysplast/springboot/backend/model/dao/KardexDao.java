package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Kardex;

public interface KardexDao extends CrudRepository<Kardex, Long> {
	
	
	@Query(value="EXEC usp_topkardexsubprod @sector = :sector, @producto = :producto", nativeQuery = true)
	public Kardex findTop1Kardex(@Param("sector") String sector, @Param("producto") String nrodoc);
	
	@Query(value="EXEC usp_consultakardex @sector = :sector, @almacen = :almacen, @producto = :producto, @fecha1 = :fecha1, @fecha2 = :fecha2", nativeQuery = true)
	public List<Kardex> filtroKardex(@Param("sector") String sector, @Param("almacen") String almacen, @Param("producto")String producto, @Param("fecha1")String fecha1,@Param("fecha2")String fecha2);


}
