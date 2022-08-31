package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.IUsuarioDao;
import com.mysplast.springboot.backend.model.entity.Usuario;


@Service
@Transactional
public class IUsuarioServiceGral {
	
	@Autowired
	private IUsuarioDao usuariorepo;
	
	public List<Usuario> listarTodo(){
		return (List<Usuario>) usuariorepo.findAll();
	}
	
	public Usuario buscarporId(Long id){
		return usuariorepo.findById(id).get();
	}
	
	public Usuario buscarporUsuario(String username){
		return usuariorepo.findByUsername(username);
	}
	
	public Usuario grabarUsuario(Usuario usuario) {
		return usuariorepo.save(usuario);
	}
	
	public void eliminarUsuario(Long id) {
		usuariorepo.deleteById(id);
	}
	
	public Usuario eliminarRoles(int id) {
		return usuariorepo.eliminarRoles(id);
	}

}
