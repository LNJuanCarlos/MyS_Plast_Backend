package com.mysplast.springboot.backend.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.IUsuarioDao;
import com.mysplast.springboot.backend.model.entity.Usuario;


@Service
public class UsuarioService implements IUsuarioService ,UserDetailsService{

	private Logger logger =  LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private IUsuarioDao usuariorepo;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuariorepo.findByUsername(username);
		
		if(usuario==null) {
			logger.error("Error en el login: No existe el usuario " +username+ " en el sistema");
			throw new UsernameNotFoundException("Error en el login: No existe el usuario " +username+ " en el sistema");
		}
		
		List<GrantedAuthority> authorities = usuario.getRol()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
				.peek(authority -> logger.info("Rol"+authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		return usuariorepo.findByUsername(username);
	}
	


}
