package br.lostpets.project.components;

import br.lostpets.project.model.PetPerdido;
import br.lostpets.project.model.Usuario;

/**
 * Form component for user and pet registration.
 * 
 * Refactored from CadastroPessoaAnimalComponent to UserPetRegistrationForm
 * for English naming consistency.
 * 
 * This component holds both Usuario (User) and PetPerdido (Lost Pet) data
 * for combined registration forms.
 */
public class UserPetRegistrationForm {

	private PetPerdido petPerdido;
	private Usuario usuario;

	public UserPetRegistrationForm() {
		petPerdido = new PetPerdido();
		usuario = new Usuario();
	}

	public PetPerdido getPetPerdido() {
		return petPerdido;
	}

	public void setPetPerdido(PetPerdido petPerdido) {
		this.petPerdido = petPerdido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
