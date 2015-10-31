	package Projecto;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class Contacto {

	private String nome;
	private ImageIcon img;
	private Conversa conversa;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Contacto(String nome, Conversa conversa, ImageIcon img) {
		super();
		this.nome = nome;
		this.conversa = conversa;
			this.img = img;

	}

	public ImageIcon getImg() {
		return img;
	}

	public Conversa getConversa() {
		return conversa;
	}
	
	@Override
	public String toString() {
		return nome;
	}

}
