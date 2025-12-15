package br.lostpets.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.lostpets.project.model.FoundAnimal;
import br.lostpets.project.model.LostPet;
import br.lostpets.project.model.User;
import br.lostpets.project.repository.FoundAnimalRepository;
import br.lostpets.project.repository.LostPetRepository;
import br.lostpets.project.repository.UserRepository;

@Service
public class FoundAnimalService {

	@Autowired
	private FoundAnimalRepository animaisRepo;
	
	@Autowired
	private UserRepository usuarioRepository;
	
	@Autowired
	private LostPetRepository petPerdidoRepository;
	
	public List<FoundAnimal> getByUser(Integer idUser){
		User usuario = usuarioRepository.getOne(idUser);
		if(usuario != null) {
			return animaisRepo.getAllByUsuario(usuario);
		}
		return null;
	}
	
	public List<FoundAnimal> getAllActive(){
		return animaisRepo.getActive();
	}
	
	public String acharAnimal(FoundAnimal animal){		
		String retorno = "";
		User usuario = usuarioRepository.getOne(animal.getUsuarioAchou().getIdPessoa());
		LostPet petPerdido = petPerdidoRepository.getAtivosByIdAnimal(animal.getPetPerdido().getIdAnimal());
		
		animal.setStatus("W");//aguarda a confirmação do dono
		animal.setUsuarioAchou(usuario);	
		animal.setPetPerdido(petPerdido);
		animal.setPontos(0);
		
		animaisRepo.save(animal);
		
		if(petPerdido.getUsuario().getIdPessoa() == animal.getUsuarioAchou().getIdPessoa()) {
			confirmarAnimalAchado(animal);
			retorno = "Seu animal foi encontrado por você mesmo.";
		}else {
			//manda email para o dono pedindo confirmação
			retorno = "Aguardando confirmação do dono do pet.";
		}
		
		return retorno;
	}
	
	public FoundAnimal confirmarAnimalAchado(FoundAnimal animal){
		
		FoundAnimal animalPersistido = animaisRepo.getOne(animal.getId());
		
		User usuario = usuarioRepository.getOne(animalPersistido.getUsuarioAchou().getIdPessoa());
		LostPet petPerdido = petPerdidoRepository.getAtivosByIdAnimal(animalPersistido.getPetPerdido().getIdAnimal());
		
		animalPersistido.setStatus("A");//animal achado
		animalPersistido.setUsuarioAchou(usuario);
		
		if(petPerdido.getStatus() == "P") {
			petPerdido.setStatus("A");
		}else {
			return null;
		}		
		
		if(petPerdido.getUsuario().getIdPessoa() == animalPersistido.getUsuarioAchou().getIdPessoa()) {
			animalPersistido.setPontos(0);
		} else {
			animalPersistido.setPontos(10);
		}
		
		LostPet petPerdidoAtualizado = petPerdidoRepository.save(petPerdido);
		animalPersistido.setPetPerdido(petPerdidoAtualizado);
		FoundAnimal insert = animaisRepo.save(animalPersistido);
		return insert;
	}	
}
