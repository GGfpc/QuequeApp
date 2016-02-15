package Projecto;

import java.io.Serializable;

import GUI.Utilizador;

public class MensagemIndividual extends Mensagem implements Serializable{
	
	private Contacto sender;
	
	public MensagemIndividual(String conteudo, Contacto c, Utilizador user, boolean sent) {
		super(user,conteudo,sent);
		this.sender = c;
	}
	
	public MensagemIndividual(String conteudo, Contacto c, Utilizador user, boolean sent, boolean received, String timestamp,String id) {
		super();
		super.setUser(user);
		super.setConteudo(conteudo);
		super.setSent(sent);
		super.setTimestamp(timestamp);
		super.setReceived(received);
		super.setId(id);
		this.sender = c;
	}
	
	public Contacto getSender() {
		return sender;
	}
	
	public void setContacto(Contacto sender) {
		this.sender = sender;
	}

}
