package br.lostpets.project.service;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.lostpets.project.model.User;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

	@Autowired
	private UserService usuarioService;
	private User usuario;
	
	@Before
	public void setup() {
		usuario = new User("joao", "joao.joao", "celular", "telefone");
		usuarioService.saveUser(usuario);
	}
	
	@Test
	public void usuarioCriado() {		
		User mesmoUsuario = usuarioService.encontrar(usuario.getIdPessoa());
		assertEquals(usuario, mesmoUsuario);
	}
	
	@Test
	public void testeBooleanEmail() {
		boolean x = usuarioService.verificarEmail("joao.joao");
		System.out.println("O RETORNO É: " + x);
		
	}
	
}
