package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.StockDao;
import com.mysplast.springboot.backend.model.entity.Stock;

@Service
@Transactional
public class StockService {
	
	@Autowired
	private StockDao productostockrepo;

	
	public List<Stock> listarStockProductos(){
		return (List<Stock>) productostockrepo.findAll();
	}
	
	public List<Stock> filtroStock(String subalm, String alm, String prod){
		return  productostockrepo.filtroStock(subalm, alm, prod);
	}
	
	public Stock buscarPorAlmacen(String subalm, String prod){
		return  productostockrepo.buscarStockdeProducto(subalm, prod);
	}
	
	public Stock grabarProductoStock(Stock stock) {
		return productostockrepo.save(stock);
	}

}
