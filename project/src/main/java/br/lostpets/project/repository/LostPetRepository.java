package br.lostpets.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.lostpets.project.model.LostPet;
import br.lostpets.project.model.User;

@Repository
public interface LostPetRepository extends JpaRepository<LostPet, Integer>{ 

	@Query("from LostPet where status = 'P'")
	List<LostPet> getAtivos();

	@Query("from LostPet where status = 'P' and idAnimal = ?1")
	public LostPet getAtivosByIdAnimal(int idAnimal);

	@Query("from LostPet where status = 'P' and pathImg is not null")
	public List<LostPet> getAtivosNNull();

	@Query("from LostPet where usuario = ?1")
	public List<LostPet> findAllByUser(User usuario);

}
