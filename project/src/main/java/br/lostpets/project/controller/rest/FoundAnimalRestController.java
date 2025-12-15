package br.lostpets.project.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.lostpets.project.model.FoundAnimal;
import br.lostpets.project.service.FoundAnimalService;
@CrossOrigin
@RestController
@RequestMapping("/animaisAchados")
public class FoundAnimalRestController {

	@Autowired
	private FoundAnimalService animaisAchadosService;
	
	@GetMapping("/byUser/{id}")
	public ResponseEntity<List<FoundAnimal>> getByUser(@PathVariable("id") Integer idUser){
		List<FoundAnimal> list = animaisAchadosService.getByUser(idUser);
		return ResponseEntity.ok(list);		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<FoundAnimal>> getAllActive(){
		List<FoundAnimal> list = animaisAchadosService.getAllActive();
		return ResponseEntity.ok(list);		
	}
	
	@PostMapping("/")
	public ResponseEntity<String> acharAnimal(@RequestBody FoundAnimal animal){
		String response = animaisAchadosService.acharAnimal(animal);
		return ResponseEntity.ok(response);
	}
	
}
