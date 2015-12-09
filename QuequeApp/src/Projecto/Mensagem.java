package Projecto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import GUI.Utilizador;

public class Mensagem implements Serializable {

	private Utilizador user;
	private String conteudo;
	private String timestamp;
	private Contacto contacto;
	private boolean sent; //indica se a mensagem foi enviada ou recebida pelo user
	private boolean received; //indica se a mensagem já foi enviada para o contacto
	private String id;
	private Contacto janela; //indica a janela para onde enviar a mensagem
	private boolean toGroup; //indica se a mensagem é para um grupo (provavelmente vou alterar)

	public Mensagem(String conteudo, Contacto sender, boolean sent,
			Utilizador user, boolean toGroup) {
		super();
		this.conteudo = conteudo;
		this.contacto = sender;
		this.toGroup = toGroup;
		this.timestamp = new Date().toString();
		this.sent = sent;
		this.user = user;
		id = UUID.randomUUID().toString(); //cria um id unico para comparar mensagens
		received = false;
	}

	
	//Este construtor é para criar as mensagens a partir do ficheiro
	public Mensagem(String conteudo, Contacto sender, Utilizador user,
			String timestamp, boolean sent, boolean received, String id) {
		super();
		this.conteudo = conteudo;
		this.contacto = sender;
		this.timestamp = timestamp;
		this.sent = sent;
		this.received = received;
		this.id = id;
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public Contacto getJanela() {
		return janela;
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

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public void setUser(Utilizador user) {
		this.user = user;
	}

	public boolean isToGroup() {
		return toGroup;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public boolean isReceived() {
		return received;
	}

	public void setJanela(Contacto janela) {
		this.janela = janela;
	}

}
