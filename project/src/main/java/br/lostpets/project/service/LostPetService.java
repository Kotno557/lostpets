package br.lostpets.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.lostpets.project.model.LostPet;
import br.lostpets.project.model.User;
import br.lostpets.project.repository.LostPetRepository;

@Service
public class LostPetService {

	@Autowired
	private LostPetRepository petPerdidoRepository;

	@Autowired
	private UserService usuarioService;

	public List<LostPet> encontrarPetsAtivos() {
		return petPerdidoRepository.getAtivos();
	}

	public LostPet encontrarUnicoPet(int id) {
		return petPerdidoRepository.getAtivosByIdAnimal(id);
	}

	public void salvarPet(LostPet petPerdido) {
		petPerdidoRepository.save(petPerdido);
	}

	public List<LostPet> encontrarTodos() {
		return petPerdidoRepository.findAll();
	}

	public List<LostPet> encontrarPetsAtivosNNull() {
		return petPerdidoRepository.getAtivosNNull();
	}

	public List<LostPet> findAllByUser(int id) {
		User usuario = usuarioService.encontrar(id);
		if (usuario == null) {
			return null;
		}
		return petPerdidoRepository.findAllByUser(usuario);
	}
	
	public List<LostPet> encontrarAnimalComONome(String nome){
		List<LostPet> pets = this.encontrarPetsAtivos();
		
		List<LostPet> petsFiltrados = pets.stream()
			    .filter(p -> p.getNomeAnimal().toUpperCase().contains(nome.toUpperCase())).collect(Collectors.toList());
		
		return petsFiltrados;
	}

}
