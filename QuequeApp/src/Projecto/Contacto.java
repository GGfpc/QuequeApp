package Projecto;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class Contacto implements Serializable {

	private String nome;
	private ImageIcon img;
	private transient Conversa conversa;
	private String path;
	private int notifications;

	public Contacto(String nome, Conversa conversa) {
		super();
		this.nome = nome;
		this.conversa = conversa;
		notifications = 0;

		// Transforma a imagem default num ImageIcon para o contacto
		img = new ImageIcon(new ImageIcon(getClass().getResource("/def.png"))
				.getImage().getScaledInstance(45, 45,
						java.awt.Image.SCALE_SMOOTH));

		// Cria o novo contacto
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ImageIcon getImg() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}

	public Conversa getConversa() {
		return conversa;
	}

	@Override
	public String toString() {
		return nome;
	}

	public void setNotifications(int notifications) {
		this.notifications = notifications;
	}

	public int getNotifications() {
		return notifications;
	}

}
