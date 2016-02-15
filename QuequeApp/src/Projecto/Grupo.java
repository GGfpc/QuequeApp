package Projecto;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Grupo extends Contacto implements Serializable{

	private transient Conversa conversa;
	private ArrayList<Contacto> contactos;
	

	public Grupo(String nome, Conversa conversa) {
		super(nome,conversa);
		this.conversa = conversa;
		contactos = new ArrayList<>();
		
		//Imagem default do grupo
		ImageIcon img = new ImageIcon(new ImageIcon(getClass().getResource("/groupdef.png"))
		.getImage().getScaledInstance(45, 45,
				java.awt.Image.SCALE_SMOOTH));
		
		super.setImg(img);
	}
	
	public void addContacto(Contacto c){
		contactos.add(c);
	}
	
	public void setContactos(ArrayList<Contacto> contactos) {
		this.contactos = contactos;
	}
	
	
	public ArrayList<Contacto> getContactos() {
		return contactos;
	}
	
	
	

}
