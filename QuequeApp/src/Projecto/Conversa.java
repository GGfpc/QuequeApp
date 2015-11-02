package Projecto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
		try {
			FileWriter writer = new FileWriter("config/user/" + u.getNome()
					+ "/" + m.getSender().getNome() + "-conversa.txt", true);
			writer.write(m.getTimestamp() + "-" + m.getSender().getNome() + "-"
					+ m.getConteudo() + "-" + m.isSent() + System.lineSeparator());
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
				String[] tokens = msg.split("-");
				boolean sent = Boolean.parseBoolean(tokens[3]);
				Mensagem mensagem = new Mensagem(tokens[2], remetente, sent);
				if (sent) {
					this.conversa.sendMessage(mensagem.getConteudo());
				} else {
					this.conversa.receiveMessage(mensagem.getConteudo(),
							remetente);
				}
				this.historico.add(mensagem);
			}
			read.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
