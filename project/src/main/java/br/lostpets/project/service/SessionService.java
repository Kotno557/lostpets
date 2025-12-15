package br.lostpets.project.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.lostpets.project.model.User;

@Service
public class SessionService {

	private final static String SESSION_USUARIO = "objUsuario";
	
	@Autowired
	private HttpSession session;
	
	public void setUserSession(User usuario) {
		this.session.setAttribute(SESSION_USUARIO, usuario);		
	}
	
	public boolean existsUserSession() {
		Object obj = this.session.getAttribute(SESSION_USUARIO);
		return obj != null;
	}
	
	public User getUsuarioSession() {
		if(!this.existsUserSession()) { return null;}		
		User usuario = (User)this.session.getAttribute(SESSION_USUARIO);
		return usuario;
	}
	
}
