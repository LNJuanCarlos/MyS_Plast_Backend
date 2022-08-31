package com.mysplast.springboot.backend.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mysplast.springboot.backend.model.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);
	
	@Query(value="EXEC usp_eliminarRoles @idusuario = :idusuario", nativeQuery = true)
	public Usuario eliminarRoles(@Param("idusuario") int idusuario);
	
	@Query(value="EXEC usp_eliminarAlmacenes @idusuario = :idusuario", nativeQuery = true)
	public Usuario eliminarAlmacenes(@Param("idusuario") int idusuario);


}
