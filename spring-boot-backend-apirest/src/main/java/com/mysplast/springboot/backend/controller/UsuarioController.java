package com.mysplast.springboot.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysplast.springboot.backend.model.entity.Rol;
import com.mysplast.springboot.backend.model.entity.Usuario;
import com.mysplast.springboot.backend.model.service.IUsuarioServiceGral;
import com.mysplast.springboot.backend.model.service.RolService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IUsuarioServiceGral usuarioService;

	@Autowired
	private RolService rolservice;

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/listar")
	public List<Usuario> listarUsuarios() {
		return usuarioService.listarTodo();
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/roles")
	public List<Rol> lostarRoles() {
		return rolservice.listarTodo();
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/crear")
	public ResponseEntity<?> create(@RequestBody Usuario usuario,
			@RequestParam("roles") List<String> roles) {
		Usuario nuevousuario = null;
		Map<String, Object> response = new HashMap<>();
		List<Rol> rolOb = new ArrayList<Rol>() ;
		
		try {
			
			for(int i=0;i<roles.size();i++) {
				Rol rol = rolservice.buscarRolId(Long.parseLong(roles.get(i)));
				rolOb.add(rol);
			}
			
			usuario.setRol(rolOb);
			usuario.setEnabled(true);
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			nuevousuario = usuarioService.grabarUsuario(usuario);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido registrado con éxito!");
		response.put("usuario", nuevousuario);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		Usuario usuario = null;

		Map<String, Object> response = new HashMap<>();

		try {
			usuario = usuarioService.buscarporId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuario == null) {
			response.put("mensaje", "El usuario con el ID:".concat(id.toString().concat("no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/estado/{id}")
	public ResponseEntity<?> actualizarestadoUsuario(@RequestBody Usuario usuario,
			@PathVariable Long id) {
		
		Usuario usuarioActual = usuarioService.buscarporId(id);
		Usuario usuarioActualizada = null;
		Map<String, Object> response = new HashMap<>();

		if (usuarioActual == null) {
			response.put("mensaje", "El usuario con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			usuarioActual.setEnabled(usuario.getEnabled());
			usuarioActualizada = usuarioService.grabarUsuario(usuarioActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido actualizado con éxito!");
		response.put("usuario", usuarioActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizarDatosUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
		Usuario usuarioActual = usuarioService.buscarporId(id);
		Usuario usuarioActualizada = null;
		Map<String, Object> response = new HashMap<>();

		if (usuarioActual == null) {
			response.put("mensaje", "El usuario con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			usuarioActual.setUsername(usuario.getUsername());
			usuarioActual.setNombre(usuario.getNombre());
			usuarioActual.setCorreo(usuario.getCorreo());
			usuarioActual.setEnabled(usuario.getEnabled());
			usuarioActualizada = usuarioService.grabarUsuario(usuarioActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido actualizado con éxito!");
		response.put("usuario", usuarioActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/actualizarpassword/{id}")
	public ResponseEntity<?> actualizarContraseñaUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
		Usuario usuarioActual = usuarioService.buscarporId(id);
		Usuario usuarioActualizada = null;
		Map<String, Object> response = new HashMap<>();

		if (usuarioActual == null) {
			response.put("mensaje", "El usuario con el ID:" + id.toString() + "no existe!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (usuarioActual.getEnabled().equals(false)) {
			response.put("mensaje", "El usuario con el ID:" + id.toString() + "ya se encuentra inactivado, actívelo para poder modificar!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			usuarioActual.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuarioActualizada = usuarioService.grabarUsuario(usuarioActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La contraseña ha sido actualizado con éxito!");
		response.put("usuario", usuarioActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			usuarioService.eliminarUsuario(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la eliminación en la base de datos!");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

}
