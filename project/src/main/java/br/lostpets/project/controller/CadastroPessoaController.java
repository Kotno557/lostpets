package br.lostpets.project.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.lostpets.project.model.User;
import br.lostpets.project.service.ImageStorageService;
import br.lostpets.project.service.UserService;

/**
 * Controller for user registration.
 * 
 * Refactoring improvements:
 * - Uses ImageStorageService to eliminate duplicated upload code
 * - Added proper logging
 * - Improved code readability
 */
@Controller
public class CadastroPessoaController {

	private static final Logger logger = LoggerFactory.getLogger(CadastroPessoaController.class);

	@Autowired
	private UserService usuarioService;
	
	@Autowired
	private ImageStorageService imageStorageService;
	
	private ModelAndView modelAndView = new ModelAndView();
	
	@Autowired
	private LoginController login;
	
	@GetMapping("/LostPets/Cadastro")
	public ModelAndView cadastroPage() {
		User usuario = new User();
		modelAndView.addObject("usuario", usuario);
		modelAndView.setViewName("cadastroPessoa");
		return modelAndView;
	}

	@PostMapping("/LostPets/Cadastro")
	public ModelAndView cadastrar(@RequestParam(value = "files") MultipartFile[] files, @Valid User usuario,
			BindingResult bindingResult) throws IOException, GeneralSecurityException  {

		// Check for validation errors
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("cadastroPessoa");
			return modelAndView;
		} 
		
		// Check if email is already registered
		if (usuarioService.verificarEmail(usuario.getEmail())) {
		modelAndView.addObject("mensagem", AlertMessages.EMAIL_ALREADY_REGISTERED.getMessage());
		}
		
		User usuario2 = usuarioService.verifyEmailUser(usuario.getEmail());
		
		// Upload profile image using ImageStorageService
		String imageUrl = imageStorageService.uploadUserProfileImage(files);
		if (imageUrl != null) {
			usuario.setIdImagem(imageUrl);
		}
		
		if (usuario2 == null) {
			// New user registration
			usuarioService.saveUser(usuario);
			logger.info("New user registered: {}", usuario.getEmail());
			return login.logar(usuario);
		} else {
			// Update existing user
			usuario2.setBairro(usuario.getBairro());
			usuario2.setCep(usuario.getCep());
			usuario2.setCidade(usuario.getCidade());
			usuario2.setRua(usuario.getRua());
			usuario2.setUf(usuario.getUf());
			usuario2.setSenha(usuario.getSenha());
			if (imageUrl != null) {
				usuario2.setIdImagem(imageUrl);
			}
			usuarioService.saveUser(usuario2);
			logger.info("Updated existing user: {}", usuario2.getEmail());
			return login.logar(usuario);
		}
	}
	
}
