package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Ingreso;
import com.mysplast.springboot.backend.model.entity.IngresosAlmacen;

public interface IngresoDao extends CrudRepository<Ingreso, String> {
	
	@Query(value="EXEC usp_consultaingreso @sector = :sector, @almacen = :almacen, @fecha1 = :fecha1, @fecha2 = :fecha2", nativeQuery = true)
	public List<Ingreso> filtroIngreso(@Param("sector") String sector, @Param("almacen") String almacen, @Param("fecha1")String fecha1,@Param("fecha2")String fecha2);

	@Query(value="EXEC usp_ingresosxalmacen @producto = :producto, @fecha1 = :fecha1, @fecha2 = :fecha2", nativeQuery = true)
	public List<IngresosAlmacen> filtroIngresosxAlmacen(@Param("producto") String producto, @Param("fecha1")String fecha1,@Param("fecha2")String fecha2);

	@Query("SELECT d FROM Ingreso d WHERE d.NRO_ORDEN = ?1")
	public Ingreso buscarPorNroOrden(String norden);
	
	public List<Ingreso> findTop50ByOrderByFECHATRANDesc();

}
