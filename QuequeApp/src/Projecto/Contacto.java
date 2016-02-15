package Projecto;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Contacto implements Serializable {

	private String nome;
	private ImageIcon img;
	private int notifications;
	private boolean online;
	private transient Conversa conversa;

	public Contacto(String nome, Conversa conversa) {
		super();
		this.nome = nome;
		this.conversa = conversa;

		// Imagem default do utilizador
		img = new ImageIcon(new ImageIcon(getClass().getResource("/def.png"))
		.getImage().getScaledInstance(45, 45,
				java.awt.Image.SCALE_SMOOTH));
		// Quando este valor é maior que 0 a lista de contactos fica verde e
		// mostra o numero
		notifications = 0;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
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
	
	public void saveImage(String user, ImageIcon img){
		RenderedImage rend;
		BufferedImage image = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(img.getImage(), 0, 0, null);
		g.dispose();
		rend = image;
		
		File imagem = new File("config/user/" + user + "/" + nome + ".jpg");
		try {
			ImageIO.write(rend, "jpg", imagem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
