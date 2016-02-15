package Projecto;

import java.util.ArrayList;

import GUI.Utilizador;

public class MensagemDeGrupo extends Mensagem {

	private ArrayList<Contacto> contactosAEnviar;
	private ArrayList<Contacto> jaEnviado;
	private boolean alreadySent;
	private Grupo grupo;
	private Contacto sender;

	public MensagemDeGrupo(String conteudo, ArrayList<Contacto> lista, Utilizador user, Grupo grupo, boolean sent) {
		super(user, conteudo, sent);
		contactosAEnviar = lista;
		jaEnviado = new ArrayList<>();
		this.grupo = grupo;
	}

	public MensagemDeGrupo(String conteudo, Contacto c, Grupo g, Utilizador user, boolean sent, boolean received, String timestamp,
			String id) {
		super();
		super.setUser(user);
		super.setConteudo(conteudo);
		super.setSent(sent);
		super.setTimestamp(timestamp);
		super.setReceived(received);
		super.setId(id);
		this.sender = c;
		grupo = g;
	}

	public ArrayList<Contacto> getContactosAEnviar() {
		return contactosAEnviar;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public ArrayList<Contacto> getJaEnviado() {
		return jaEnviado;
	}

	public void setAlreadySent(boolean alreadySent) {
		this.alreadySent = alreadySent;
	}

	public boolean isAlreadySent() {
		return alreadySent;
	}

	public void setSender(Contacto sender) {
		this.sender = sender;
	}

	public Contacto getSender() {
		return sender;
	}

}
