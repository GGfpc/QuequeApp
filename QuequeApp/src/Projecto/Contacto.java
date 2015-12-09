package Projecto;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class Contacto implements Serializable {

	private String nome;
	private ImageIcon img;	
	private int notifications;

	private transient Conversa conversa;

	public Contacto(String nome, Conversa conversa) {
		super();
		this.nome = nome;
		this.conversa = conversa;
		
		//Imagem default do utilizador
		
		img = new ImageIcon(new ImageIcon(getClass().getResource("/def.png"))
		.getImage().getScaledInstance(45, 45,
				java.awt.Image.SCALE_SMOOTH));
		
		
		//Quando este valor é maior que 0 a lista de contactos fica verde e mostra o numero
		notifications = 0;
	}

	public Conversa getConversa() {
		return conversa;
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
