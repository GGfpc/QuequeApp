package GUI;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import Projecto.Contacto;

public class Utilizador{
	
	ArrayList<Contacto> contactosDoUtilizador;
	String nome;
	
	
	public Utilizador(String nome) {;
		contactosDoUtilizador = new ArrayList<>();
		this.nome = nome;
		criaConfig();
		
	}
	
	public void novoContacto(Contacto remetente) {
		contactosDoUtilizador.add(remetente);
		System.out.println(remetente);
	}
	
	public String getNome() {
		return nome;
	}
	
	private void criaConfig() {
		File userDir = new File("config/user/" + nome);
		userDir.mkdirs();
		
		File configFile = new File("config/user/" + nome + "/configfile.txt");
		try {
			configFile.createNewFile();
			FileWriter filewriter = new FileWriter(configFile.getAbsoluteFile());
			BufferedWriter writer = new BufferedWriter(filewriter);
			writer.write("Ola, tudo bem?");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
