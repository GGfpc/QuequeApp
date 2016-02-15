package Rede;

import java.io.Serializable;

import javax.swing.ImageIcon;

import GUI.Utilizador;

public class Info implements Serializable{
	
	String user;
	ClientState state;
	ImageIcon pic;
	
	public Info(String user, ClientState state) {
		super();
		this.user = user;
		this.state = state;
	}
	
	
	
	

}
