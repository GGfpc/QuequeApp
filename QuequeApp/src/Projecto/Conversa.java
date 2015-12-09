package Projecto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import GUI.JanelaDeConversa;
import GUI.Utilizador;

public class Conversa {

	private ArrayList<Mensagem> historico = new ArrayList<>();
	private JanelaDeConversa conversa;
	private Utilizador u;

	public Conversa(JanelaDeConversa conversa, Utilizador u) {
		super();
		this.conversa = conversa;
		this.u = u;
	}

	public JanelaDeConversa getConversa() {
		return conversa;
	}

	public void novaMensagem(Mensagem m) {
		historico.add(m);
		System.out.println("Escrito");
		writeToFile(m);
	}
	
	//Escreve as mensagens no ficheiro com os parametros separados por ;
	//Provavelmente vou alterar

	private void writeToFile(Mensagem m) {
		try {
			FileWriter writer = new FileWriter("config/user/" + u.getNome()
					+ "/" + m.getSender().getNome() + "-conversa.txt", true);
			writer.write(m.getTimestamp() + ";" + m.getSender().getNome() + ";"
					+ m.getConteudo() + ";" + m.isSent() + ";" + m.isReceived() + ";" + m.getId() + ";" + m.getJanela() + System.lineSeparator());
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadConversa(Contacto remetente) {
		File conversa = new File("config/user/" + u.getNome() + "/"
				+ remetente.getNome() + "-conversa.txt");

		try {
			BufferedReader read = new BufferedReader(new FileReader(conversa));
			String msg;

			while ((msg = read.readLine()) != null) {
				
				//faz o mesmo que o obtem_substring de SO
				String[] tokens = msg.split(";");
				//Transforma strings em boleanos
				boolean sent = Boolean.parseBoolean(tokens[3]);
				boolean received = Boolean.parseBoolean(tokens[4]);
				//Cria a mensagem com base dos valores lidos
				Mensagem mensagem = new Mensagem(tokens[2], remetente,u,tokens[0],sent,received,tokens[5]);
				mensagem.setJanela(remetente);
				if (sent) {
					this.conversa.sendMessage(mensagem, true);
				} else {
					this.conversa.receiveMessage(mensagem.getConteudo(),
							remetente);
				}
				//Se for mensagem de grupo adiciona o utilizador que enviou ao grupo
				//Provavelmente vou alterar
				if(mensagem.getJanela() instanceof Grupo && !(remetente instanceof Grupo)){
					((Grupo) mensagem.getJanela()).getContactos().add(remetente);
				}
				
				this.historico.add(mensagem);
			}
			read.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//Altera a cor da mensagem quando é recebida pelo outro
	public void setSent(Mensagem m){
		for(Mensagem msg : historico){
			if(m.getId().equals(msg.getId())){
				msg.setReceived(true);
				//altera a bolha
				conversa.setSent(m);
			}
		}
	}
	
	
	//Guarda todas as mensagens quando a janela é fechada
	public void saveConversa(Contacto remetente) throws IOException{
		FileWriter writer = new FileWriter("config/user/" + u.getNome()
				+ "/" + remetente.getNome() + "-conversa.txt");
		writer.close();
		
		for(Mensagem m : historico){
			System.out.println(m.isReceived());
			writeToFile(m);
		}
	}

}
