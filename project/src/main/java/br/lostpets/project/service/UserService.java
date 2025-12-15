package br.lostpets.project.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.lostpets.project.model.FoundAnimal;
import br.lostpets.project.model.Address;
import br.lostpets.project.model.UserPoints;
import br.lostpets.project.model.User;
import br.lostpets.project.repository.FoundAnimalRepository;
import br.lostpets.project.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository usuarioRepository;

	@Autowired
	private FoundAnimalRepository animaisAchados;

	public List<User> encontrarTodos() {
		return usuarioRepository.findAll();
	}

	public User encontrar(int id) {
		return usuarioRepository.getUser(id);
	}
	
	public User saveUser(User usuario) {

		if (usuario.getCep() != null) {
			ViaCep viaCep = new ViaCep();
			Address address = viaCep.getLatitudeLongitude(usuario.getCep());
			usuario.setLatitude(address.getLatitude());
			usuario.setLongitude(address.getLongitude());
		}
		return usuarioRepository.save(usuario);
	}

	public User emailSenha(String email, String senha) {
		return usuarioRepository.validarAcesso(email, senha);
	}

	public User verifyEmailUser(String email) {
		return usuarioRepository.encontrarEmail(email);
	}

	public boolean verificarEmail(String email) {
		User usuario = usuarioRepository.encontrarEmail(email);
		if (usuario != null) {
			return (usuario.getCep() != null) && (usuario.getSenha() != null);
		}
		return false;
	}

	public int totalUserPoints(Integer idUser) {
		User usuario = usuarioRepository.getOne(idUser);
		
		if(usuario == null) {
			return 0;
		}
		if(usuario.getSenha() == null) {
			return 0;
		}
		
		Integer total = animaisAchados.totalUserPoints(usuario);
		if (total == null) {
			return 0;
		}
		return total;
	}

	public List<UserPoints> totalUserPointsTodosUsuario() {
		List<FoundAnimal> animaisEncontrados = animaisAchados.findAllByStatus("A");
		List<User> usuarios = usuarioRepository.findAll();
		usuarios.removeIf(usuario -> usuario.getSenha() == null);
		List<UserPoints> pontosUsuario = new ArrayList<>();

		for (int i = 0; i < usuarios.size(); i++) {
			User u = usuarios.get(i);
			int petsAchados = 0;
			pontosUsuario.add(new UserPoints(u.getIdPessoa(), 0, u.getNome(), petsAchados));
			for (int j = 0; j < animaisEncontrados.size(); j++) {
				FoundAnimal animal = animaisEncontrados.get(j);
				if (u.getIdPessoa() == animal.getUsuarioAchou().getIdPessoa()) {
					int pontos = pontosUsuario.get(i).getPontos() + animal.getPontos();
					pontosUsuario.get(i).setPontos(pontos);
					pontosUsuario.get(i).setQuantidadePetsAchados(++petsAchados);
				}
			}
		}
		pontosUsuario.removeIf(usuario -> usuario.getPontos() < 1);
		
		pontosUsuario.sort(Comparator.comparing(UserPoints::getPontos).reversed());
		return pontosUsuario;
	}

	/*
	 * public void delete(Long id) { usuarioRepository.delete(id); }
	 */

}
