package Projecto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import GUI.Utilizador;

public class Mensagem implements Serializable {
	
	private Utilizador user;
	private String conteudo;
	private String timestamp;
	private Contacto contacto;
	private boolean sent;
	
	
	public Mensagem(String conteudo, Contacto sender, boolean sent, Utilizador user) {
		super();
		this.conteudo = conteudo;
		this.contacto = sender;
		this.timestamp = new Date().toString();
		this.sent = sent;
		this.user = user;
	}
	
	public Mensagem(String conteudo, Contacto sender, String timestamp) {
		super();
		this.conteudo = conteudo;
		this.contacto = sender;
		this.timestamp = timestamp;
	}


	public String getConteudo() {
		return conteudo;
	}

	public Utilizador getUser() {
		return user;
	}

	public boolean isSent() {
		return sent;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {
		return conteudo;
	}
	
	public Contacto getSender() {
		return contacto;
	}
	
	
	

}
