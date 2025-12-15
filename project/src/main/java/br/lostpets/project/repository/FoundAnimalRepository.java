package br.lostpets.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.lostpets.project.model.FoundAnimal;
import br.lostpets.project.model.User;

@Repository
public interface FoundAnimalRepository  extends JpaRepository<FoundAnimal, Integer>{

	@Query("from FoundAnimal where usuarioAchou = ?1")
	List<FoundAnimal> getAllByUsuario(User usuario);
	
	@Query("from FoundAnimal where status = 'A'")
	List<FoundAnimal> getActive();

	@Query("select sum(pontos) from FoundAnimal where usuarioAchou = ?1")
	Integer totalUserPoints(User usuario);

	List<FoundAnimal> findAllByStatus(String string);
	
}
