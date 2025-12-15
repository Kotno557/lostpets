package br.lostpets.project.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import br.lostpets.project.model.FoundAnimal;
import br.lostpets.project.model.LostPet;
import br.lostpets.project.model.User;

@Transactional

@SpringBootTest
@RunWith(SpringRunner.class)
public class FoundAnimalRepositoryTest {

	@Autowired
private FoundAnimalRepository animaisAchadosRepository;

	@Autowired
	private LostPetRepository petPerdidoRepository;

	@Autowired
	private UserRepository usuarioRepository;
	
	private LostPet petPerdido;
	private User usuario;
	private FoundAnimal achados;
	
	
	@Before
	public void init() {
		usuario = new User("mateus", "mateus@lost.com", "(11) 91234-1234", "(11) 1234-1234");
		usuarioRepository.save(usuario);
		petPerdido = new LostPet(usuario,"tobias", "12/12/2018", "Descrição perdido","Gato","C://Path","00.000.000",0,0);
		petPerdidoRepository.save(petPerdido);
		achados = new FoundAnimal(usuario, petPerdido, new Date(), 10, "latitude", "longitude");
		animaisAchadosRepository.save(achados);
	}
	
	@Test
	public void cadastrarAnimalAchado() {
		FoundAnimal pet = animaisAchadosRepository.getOne(achados.getId());
		assertEquals(achados, pet);
	}
	
}
