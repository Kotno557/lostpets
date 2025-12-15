package br.lostpets.project.components;

import br.lostpets.project.model.LostPet;
import br.lostpets.project.model.User;

/**
 * Form component for user and pet registration.
 * 
 * Refactored from CadastroPessoaAnimalComponent to UserPetRegistrationForm
 * for English naming consistency.
 * 
 * This component holds both User (User) and LostPet (Lost Pet) data
 * for combined registration forms.
 */
public class UserPetRegistrationForm {

	private LostPet petPerdido;
	private User usuario;

	public UserPetRegistrationForm() {
		petPerdido = new LostPet();
		usuario = new User();
	}

	public LostPet getPetPerdido() {
		return petPerdido;
	}

	public void setPetPerdido(LostPet petPerdido) {
		this.petPerdido = petPerdido;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
}
