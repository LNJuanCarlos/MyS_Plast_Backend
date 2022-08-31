package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Recetaprod;

public interface RecetaprodDao extends CrudRepository<Recetaprod, String> {
	
	@Query(value="EXEC usp_consultareceta @nroreceta = :nroreceta, @producto = :producto, @nomreceta = :nomreceta", nativeQuery = true)
	public List<Recetaprod>filtrarRecetas(@Param("nroreceta") String nroreceta, @Param("producto")String producto,@Param("nomreceta")String nomreceta);
	
	@Query(value="SELECT * FROM Recetaprod WHERE ID_PRODUCTO  =:producto AND ESTADO = 'A'", nativeQuery = true)
	public Recetaprod buscarRecetaxProducto (@Param("producto")String producto);

}
