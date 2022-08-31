package com.mysplast.springboot.backend.model.service;

import com.mysplast.springboot.backend.model.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);	
}
