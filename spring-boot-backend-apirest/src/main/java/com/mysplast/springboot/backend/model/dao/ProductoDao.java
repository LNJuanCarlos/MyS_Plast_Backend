package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Producto;

public interface ProductoDao extends CrudRepository<Producto, String> {
	
	@Query(value="EXEC usp_consultaproducto @categoria = :categoria, @nombre = :nombre, @marca = :marca", nativeQuery = true)
	public List<Producto>filtrarProductos(@Param("categoria") String categoria, @Param("nombre")String nombre,@Param("marca")String marca);
	

	@Query(value = "SELECT * FROM PRODUCTO WHERE nom_PRODUCTO like CONCAT('%',:term,'%') AND FLAG_PRODUCCION = 'Y'", nativeQuery=true)
	public List<Producto> buscarProductosDeProduccion(String term);
	
	@Query(value = "SELECT * FROM PRODUCTO WHERE nom_PRODUCTO like CONCAT('%',:term,'%') AND FLAG_INSUMO = 'Y'", nativeQuery=true)
	public List<Producto> buscarProductosDeInsumos(String term);
	
	public List<Producto> findByNOMBREContainingIgnoreCase(String term);

}
