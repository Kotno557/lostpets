package br.lostpets.project.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.lostpets.project.model.LostPet;
import br.lostpets.project.service.LostPetService;

@RestController
@RequestMapping("/petperdido")
public class LostPetsRestController {

	@Autowired
	LostPetService petPerdidoService;

	@CrossOrigin

	@GetMapping("/usuario/{id}")
	public ResponseEntity<List<LostPet>> getAllPetsPerdidosUsuario(@PathVariable("id") int id) {
		List<LostPet> listPets = petPerdidoService.findAllByUser(id);
		return ResponseEntity.ok(listPets);
	}

	@CrossOrigin
	@GetMapping("/")
	public ResponseEntity<List<LostPet>> getAllPetsPerdidosActive() {
		List<LostPet> listPets = petPerdidoService.encontrarPetsAtivos();
		return ResponseEntity.ok(listPets);
	}

	@CrossOrigin
	@GetMapping("/{idAnimal}")
	public ResponseEntity<LostPet> getAllPetsPerdidosActiveById(@PathVariable("idAnimal") int idAnimal) {
		LostPet listPets = petPerdidoService.encontrarUnicoPet(idAnimal);
		return ResponseEntity.ok(listPets);
	}

	@CrossOrigin
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<LostPet>> getAllPetsPerdidosByNome(@PathVariable("nome") String nome) {
		List<LostPet> listPets = petPerdidoService.encontrarAnimalComONome(nome);
		return ResponseEntity.ok(listPets);
	}

}
