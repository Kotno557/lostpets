package br.lostpets.project.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.lostpets.project.model.LostPet;
import br.lostpets.project.model.User;
import br.lostpets.project.service.LostPetService;
import br.lostpets.project.service.UserService;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class LostPetRepositoryTest {

	@Autowired
private LostPetService petPerdidoService;

	@Autowired
	private UserService usuarioService;
	
	private LostPet petPerdido;
	private User usuario;
	
	
	@Before
	public void init() {
		usuario = new User("mateus", "mateus.covos@gmail.com", "(11) 91234-1234", "(11) 1234-1234");
		usuarioService.saveUser(usuario);
		petPerdido = new LostPet(usuario,"rex", "12/12/2018", "Animal Perdido","Cachorro","https://cdn.newsapi.com.au/image/v1/67a523605bca40778c6faaad93883a3b","08473300",-23.57335879,-46.39390111);
		petPerdidoService.salvarPet(petPerdido);
	}
	
	@Test
	public void getAnimalPorId() {
		LostPet pet = petPerdidoService.encontrarUnicoPet(petPerdido.getIdAnimal());
		assertEquals(petPerdido, pet);
	}
	
	//@Test
	public void pegarTodosPetPerdido() {
		List<LostPet> list = petPerdidoService.encontrarTodos();
		assertTrue(list.size() > 0);
	}
	
	//@Test
	public void pegarTodosPetPerdidoAtivo() {
		List<LostPet> list = petPerdidoService.encontrarPetsAtivos();
		assertTrue(list.size() > 0);
	}
	
}
