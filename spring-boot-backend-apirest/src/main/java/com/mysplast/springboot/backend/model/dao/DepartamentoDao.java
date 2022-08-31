package com.mysplast.springboot.backend.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.mysplast.springboot.backend.model.entity.Departamento;

public interface DepartamentoDao extends CrudRepository<Departamento, String> {

}
