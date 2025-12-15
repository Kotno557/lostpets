package br.lostpets.project.controller;

/**
 * Enum for alert messages displayed to users.
 * 
 * Refactored from MensagensAlertas to AlertMessages for English naming consistency.
 * Contains HTML-formatted alert messages for various user actions.
 */
public enum AlertMessages {

	EMPTY(""),
	
	EMAIL_PASSWORD_INCORRECT("<div id=\"card-alert\" class=\"card red\">\r\n" + 
			"                      <div class=\"card-content white-text\">\r\n" + 
			"                        <p><font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">" +
			" 							AVISO: Email ou senha INCORRETO" +
			"						</font></font></p>\r\n" + 
			"                      </div>\r\n" + 
			"                    </div>"),
	
	EMAIL_ALREADY_REGISTERED("<div id=\"card-alert\" class=\"card red\">\r\n" + 
			"                      <div class=\"card-content white-text\">\r\n" + 
			"                        <p><font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">" +
			" 							AVISO: Email já cadastrado" +
			"						</font></font></p>\r\n" + 
			"                      </div>\r\n" + 
			"                    </div>"),
	
	PET_REGISTERED_SUCCESS("<div id=\"card-alert\" class=\"card green\">\r\n" + 
			"                      <div class=\"card-content white-text\">\r\n" + 
			"                        <p><font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">" +
			" 							INFO: Animal cadastrado com SUCESSO" +
			"						</font></font></p>\r\n" + 
			"                      </div>\r\n" + 
			"                    </div>");
	
	private final String message;
	
	AlertMessages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
