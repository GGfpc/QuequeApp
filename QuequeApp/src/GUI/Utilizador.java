package GUI;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import Projecto.Contacto;

public class Utilizador{
	
	ArrayList<Contacto> contactosDoUtilizador;
	
	
	public Utilizador() {;
		contactosDoUtilizador = new ArrayList<>();
	}
	
	public void novoContacto(Contacto remetente) {
		contactosDoUtilizador.add(remetente);
	}
	
	
	
	

}
