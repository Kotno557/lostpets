package br.lostpets.project.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.lostpets.project.components.CadastroPessoaAnimalComponent;
import br.lostpets.project.model.Endereco;
import br.lostpets.project.model.PetPerdido;
import br.lostpets.project.model.Usuario;
import br.lostpets.project.service.AddressService;
import br.lostpets.project.service.ImageStorageService;
import br.lostpets.project.service.PdfRequestService;
import br.lostpets.project.service.PetPerdidoService;
import br.lostpets.project.service.UsuarioService;

/**
 * Controller for lost pet registration.
 * 
 * Refactoring improvements:
 * - Uses ImageStorageService to eliminate duplicated upload code
 * - Uses AddressService for coordinate lookup
 * - Added proper logging
 * - Improved error handling
 */
@Controller
public class CadastroAnimalController {

	private static final Logger logger = LoggerFactory.getLogger(CadastroAnimalController.class);

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PdfRequestService pdf;

	@Autowired
	private PetPerdidoService petPerdidoService;
	
	@Autowired
	private ImageStorageService imageStorageService;
	
	@Autowired
	private AddressService addressService;
	
	private ModelAndView modelAndView;
	private static boolean cadastrado;

	@GetMapping("/LostPets/Cadastro-Animal-Perdido")
	public ModelAndView PaginaCadastroAnimalPerdido() {
		modelAndView = new ModelAndView();
		CadastroPessoaAnimalComponent cadastroPessoaAnimal = new CadastroPessoaAnimalComponent();
		modelAndView.addObject("pet", cadastroPessoaAnimal);
		modelAndView.setViewName("cadastroAnimalPerdido");
		return modelAndView;
	}

	@PostMapping("/LostPets/Cadastro-Animal-Perdido")
	public ModelAndView cadastroAnimalPerdido(@RequestParam(value = "files") MultipartFile[] files,
			CadastroPessoaAnimalComponent cadastroPessoaAnimal) throws IOException, GeneralSecurityException {

		Usuario usuario1 = cadastroPessoaAnimal.getUsuario();
		PetPerdido petPerdido = cadastroPessoaAnimal.getPetPerdido();

		// Check if user already exists
		Usuario usuario = usuarioService.verificarEmailUsuario(usuario1.getEmail());
		
		// Get coordinates from CEP using AddressService
		Endereco endereco = addressService.getCoordinatesFromCep(petPerdido.getCep());

		petPerdido.setStatus("P");
		if (endereco != null) {
			petPerdido.setLatitude(endereco.getLatitude());
			petPerdido.setLongitude(endereco.getLongitude());
		} else {
			logger.warn("Could not retrieve coordinates for CEP: {}", petPerdido.getCep());
		}

		// Upload image using ImageStorageService
		String imageUrl = imageStorageService.uploadPetImage(files);
		if (imageUrl != null) {
			petPerdido.setPathImg(imageUrl);
		} else {
			logger.warn("No image uploaded for pet: {}", petPerdido.getNomeAnimal());
		}

		// Save pet with existing or new user
		if (usuario != null) {
			petPerdido.setUsuario(usuario);
			petPerdidoService.salvarPet(petPerdido);
			logger.info("Saved pet {} for existing user {}", petPerdido.getNomeAnimal(), usuario.getEmail());
		} else {
			usuario1 = usuarioService.salvarUsuario(usuario1);
			petPerdido.setUsuario(usuario1);
			petPerdidoService.salvarPet(petPerdido);
			logger.info("Saved pet {} for new user {}", petPerdido.getNomeAnimal(), usuario1.getEmail());
		}
		
		cadastrado = true;
		
		return new ModelAndView("redirect:/LostPets?pdf="+pdf.downloadPdf(petPerdido));
	}

	static boolean isCadastrado() {
		return cadastrado;
	}

	public static void setCadastrado(boolean cadastradoRef) {
		cadastrado = cadastradoRef;
	}
	
}
