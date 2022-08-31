package com.mysplast.springboot.backend.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.ItemtransaccionDao;
import com.mysplast.springboot.backend.model.entity.Itemtransaccion;

@Service
@Transactional
public class ItemtransaccionService {
	
	@Autowired
	private ItemtransaccionDao itemtransaccionrepo;
	
	public Itemtransaccion grabarItemtransaccion(Itemtransaccion itemtransaccion) {
		return itemtransaccionrepo.save(itemtransaccion);
	}

}
