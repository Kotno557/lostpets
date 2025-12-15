package br.lostpets.project.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.lostpets.project.model.UserPoints;
import br.lostpets.project.model.User;
import br.lostpets.project.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class UserRestController {

	@Autowired
	private UserService usuarioService;
	
	@GetMapping("")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> usuarios = usuarioService.encontrarTodos();
		usuarios.removeIf(u -> u.getSenha() == null);
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getAllUsers(@PathVariable("id") Integer id) {
		User usuario = usuarioService.encontrar(id);
		return ResponseEntity.ok(usuario);
	}
	
	@GetMapping("/pontos/{id}")
	public ResponseEntity<Integer> getTotalPontos(@PathVariable("id") Integer idUser) {
		Integer total = usuarioService.totalUserPoints(idUser);
		return ResponseEntity.ok(total);
	}
	
	@GetMapping("/pontos")
	public ResponseEntity<List<UserPoints>> getTotalPontosTodosUsuario() {
		List<UserPoints> pontosUsuario = usuarioService.totalUserPointsTodosUsuario();
		return ResponseEntity.ok(pontosUsuario);
	}
	
}
