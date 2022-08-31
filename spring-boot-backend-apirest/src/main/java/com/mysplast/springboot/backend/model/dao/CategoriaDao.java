package com.mysplast.springboot.backend.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.mysplast.springboot.backend.model.entity.Categoria;

public interface CategoriaDao extends CrudRepository<Categoria, String> {

}
