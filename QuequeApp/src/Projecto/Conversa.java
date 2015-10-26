package Projecto;

import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Conversa {
	
	private ArrayList<Mensagem> historico = new ArrayList<>();
	private JTextArea conversa;
	
	
	public Conversa(JTextArea conversa) {
		super();
		this.conversa = conversa;
		
		
		for(Mensagem txt : historico){
			conversa.setText(conversa.getText() + "\n" + txt);
		}
	}


	public void addMessage(Mensagem mensagem) {
		// TODO Auto-generated method stub

	}


	public JTextArea getConversa() {
		return conversa;
	}

	
	
	
	
	
	
	

}
