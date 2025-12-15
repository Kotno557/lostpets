package br.lostpets.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.lostpets.project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("from User where email = ?1")
	public User encontrarEmail(String email);

	@Query("from User where email = ?1 and senha = ?2")
	public User validarAcesso(String email, String senha);

	@Query("from User")
	public List<User> allUsers();

	@Query("from User where idPessoa = ?1")
	public User getUser(int id);

}
