package com.mysplast.springboot.backend.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.mysplast.springboot.backend.model.entity.Almacen;

public interface AlmacenDao extends CrudRepository<Almacen, String> {

}
