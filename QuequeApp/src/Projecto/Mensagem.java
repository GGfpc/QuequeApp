package Projecto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Mensagem {
	
	private String conteudo;
	private String timestamp;
	private Contacto sender;
	private boolean sent;
	
	
	public Mensagem(String conteudo, Contacto sender, boolean sent) {
		super();
		this.conteudo = conteudo;
		this.sender = sender;
		this.timestamp = new Date().toString();
		this.sent = sent;	
	}
	
	public Mensagem(String conteudo, Contacto sender, String timestamp) {
		super();
		this.conteudo = conteudo;
		this.sender = sender;
		this.timestamp = timestamp;
	}


	public String getConteudo() {
		return conteudo;
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
		return sender;
	}
	

}
