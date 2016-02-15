package Projecto;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import GUI.Utilizador;

public class Mensagem implements Serializable {

	private Utilizador user;
	private String conteudo;
	private String timestamp;
	private boolean sent; //indica se a mensagem foi enviada ou recebida pelo user
	private boolean received; //indica se a mensagem já foi enviada para o contacto
	private String id;
	private ImageIcon pic;
	private boolean haspic;
	
	public Mensagem() {
	}
	
	public Mensagem(Utilizador user, String conteudo, boolean sent) {
		id = UUID.randomUUID().toString();
		timestamp = new Date().toString();
		this.user = user;
		this.conteudo = conteudo;
		this.sent = sent;
	}
	
	public void setHaspic(boolean haspic) {
		this.haspic = haspic;
	}

	public ImageIcon getPic() {
		return pic;
	}


	public void setPic(ImageIcon pic) {
		this.pic = pic;
	}



	public boolean isHaspic() {
		return haspic;
	}



	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	/*
	public Mensagem(String conteudo, Contacto sender, boolean sent,
			Utilizador user) {
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
	
	*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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


	public void storePic(ImageIcon img,String id){
		RenderedImage rend;
		BufferedImage image = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(img.getImage(), 0, 0, null);
		g.dispose();
		rend = image;
		
		File imagem = new File("config/user/" + user.getNome() + "/"+ "/pics/" + id + ".jpg");
		try {
			ImageIO.write(rend, "jpg", imagem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public void setUser(Utilizador user) {
		this.user = user;
	}

	
	public void setReceived(boolean received) {
		this.received = received;
	}

	public boolean isReceived() {
		return received;
	}

}
