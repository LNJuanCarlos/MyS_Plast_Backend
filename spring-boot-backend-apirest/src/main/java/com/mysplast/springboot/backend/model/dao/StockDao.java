package com.mysplast.springboot.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Stock;

public interface StockDao  extends CrudRepository<Stock, Long> {
	
	@Query("SELECT ps FROM Stock ps WHERE ps.id_SECTOR.ID_SECTOR = ?1 AND ps.id_PRODUCTO.ID_PRODUCTO = ?2")
	public Stock buscarStockdeProducto(String subalm, String prod);
	
	@Query(value="EXEC usp_consultastock @sector = :sector, @almacen = :almacen, @producto = :producto", nativeQuery = true)
	public List<Stock> filtroStock(@Param("sector") String sector, @Param("almacen") String almacen, @Param("producto")String producto);



}
