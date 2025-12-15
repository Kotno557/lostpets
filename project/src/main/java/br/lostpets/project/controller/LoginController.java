package br.lostpets.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.lostpets.project.model.LostPet;
import br.lostpets.project.model.User;
import br.lostpets.project.service.LostPetService;
import br.lostpets.project.service.SessionService;
import br.lostpets.project.service.UserService;
import br.lostpets.project.utils.HistoricoAcessoLog;

@Controller
public class LoginController {

	@Autowired
	private LostPetService petPerdidoService;
	@Autowired
	private UserService usuarioService;
	@Autowired
	private HistoricoAcessoLog historicoAcessoLog;
	@Autowired
	private SessionService session;
	
	private User usuario;
	private ModelAndView modelAndView;
	private boolean msn = false; 	
	
	@RequestMapping(value = { "/", "/LostPets" }, method = RequestMethod.GET)
	public String loginPage(User usuario, Model model) {
		List<LostPet> pets;		
		pets = petPerdidoService.encontrarPetsAtivosNNull();
		model.addAttribute("fotoAnimais", pets);
		System.err.println("cadastroAnimalController: "+CadastroAnimalController.isCadastrado());
		if (session.existsUserSession()) {
			historicoAcessoLog.dataHora(usuario.getNome());
			return "redirect:/Dashboard";
			
		} else {
			String mensagem;
			if(msn) {
				msn = !msn;
				mensagem = AlertMessages.EMAIL_PASSWORD_INCORRECT.getMessage();
			}
			else if(CadastroAnimalController.isCadastrado()) {
				CadastroAnimalController.setCadastrado(false);
				mensagem = AlertMessages.PET_REGISTERED_SUCCESS.getMessage();
			}
			else {
				mensagem = AlertMessages.EMPTY.getMessage(); 
			}
			
			model.addAttribute("mensagem", mensagem);
			return "login";	
		}		
	}

	@PostMapping("/LostPets")
	public ModelAndView logar(@Valid User usuario) {
		
		usuario = usuarioService.emailSenha(usuario.getEmail(), usuario.getSenha());
		if (usuario != null) {
			modelAndView = new ModelAndView("redirect:/Dashboard");
			historicoAcessoLog.dataHora(usuario.getNome());
			session.setUserSession(usuario);
			
		} else {
			modelAndView = new ModelAndView("redirect:/LostPets");
			msn = true;
		}
		return modelAndView;
	}

	@GetMapping("/logoff")
	public ModelAndView logoff() {
		session.setUserSession(null);
		modelAndView = new ModelAndView();
		usuario = new User();
		modelAndView.addObject("usuario", usuario);
		modelAndView.setViewName("redirect:/LostPets");
		return modelAndView;
	}

}
