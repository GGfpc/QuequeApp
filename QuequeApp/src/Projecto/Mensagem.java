package Projecto;

import java.util.Date;

public class Mensagem {
	
	private String conteudo;
	private Date timestamp;
	private Contacto sender;
	
	
	public Mensagem(String conteudo, Contacto sender) {
		super();
		this.conteudo = conteudo;
		this.sender = sender;
		this.timestamp = new Date();
	}


	public String getConteudo() {
		return conteudo;
	}


	public Date getTimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {
		return conteudo;
	}
	

}
