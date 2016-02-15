package Projecto;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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

	// Escreve as mensagens no ficheiro com os parametros separados por ;
	// Provavelmente vou alterar

	private void writeToFile(Mensagem m) {
		System.out.println(m.isHaspic());
		String mensagemlog = null;
		String conversa = null;

		if (m instanceof MensagemDeGrupo) {

			if (((MensagemDeGrupo) m).getGrupo().getNome() != null) {
				conversa = "config/user/" + u.getNome() + "/" + ((MensagemDeGrupo) m).getGrupo().getNome() + "-conversa.txt";
			}
			mensagemlog = m.getTimestamp() + ";" + ((MensagemDeGrupo) m).getSender() + ";" + m.getConteudo() + ";" + m.isSent() + ";"
					+ m.isReceived() + ";" + m.getId() + ";" + m.isHaspic() + ";" + ((MensagemDeGrupo) m).getGrupo()
					+ System.lineSeparator();

		} else if (m instanceof MensagemIndividual) {
			conversa = "config/user/" + u.getNome() + "/" + ((MensagemIndividual) m).getSender().getNome() + "-conversa.txt";

			mensagemlog = m.getTimestamp() + ";" + ((MensagemIndividual) m).getSender() + ";" + m.getConteudo() + ";" + m.isSent()
					+ ";" + m.isReceived() + ";" + m.getId() + ";" + m.isHaspic() + System.lineSeparator();

		}

		try {
			FileWriter writer = new FileWriter(conversa, true);
			writer.write(mensagemlog);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadConversa(Contacto remetente) {
		File conversa = new File("config/user/" + u.getNome() + "/" + remetente.getNome() + "-conversa.txt");

		try {
			BufferedReader read = new BufferedReader(new FileReader(conversa));
			String msg;

			while ((msg = read.readLine()) != null) {

				// faz o mesmo que o obtem_substring de SO
				String[] tokens = msg.split(";");
				// Transforma strings em boleanos
				boolean sent = Boolean.parseBoolean(tokens[3]);
				boolean received = Boolean.parseBoolean(tokens[4]);
				boolean haspic = Boolean.parseBoolean(tokens[6]);
				// Cria a mensagem com base dos valores lidos
				Mensagem mensagem = null;

				if (remetente instanceof Grupo) {
					Contacto sender = u.getContacto(tokens[1]);
					mensagem = new MensagemDeGrupo(tokens[2], sender, ((Grupo) remetente), u, sent, received, tokens[0], tokens[5]);
					if (sent) {
						if (haspic) {
							BufferedImage img = null;
							try {
								File imgfile = new File("config/user/" + u.getNome() + "/pics/" + tokens[5] + ".jpg");
								img = ImageIO.read(imgfile);
							} catch (IOException e) {
							}
							mensagem.setHaspic(haspic);
							ImageIcon icon = new ImageIcon(img);
							this.conversa.sendImg(icon);
						} else {
							this.conversa.sendMessage(mensagem, true);
						}
					} else {
						if (haspic) {
							BufferedImage img = null;
							try {
								File imgfile = new File("config/user/" + u.getNome() + "/pics/" + tokens[5] + ".jpg");
								img = ImageIO.read(imgfile);
							} catch (IOException e) {
							}
							mensagem.setHaspic(haspic);
							ImageIcon icon = new ImageIcon(img);
							this.conversa.receiveImg(icon, remetente);
						} else {
							this.conversa.receiveMessage(mensagem.getConteudo(), sender);
						}
					}
				} else if (remetente instanceof Contacto) {
					mensagem = new MensagemIndividual(tokens[2], remetente, u, sent, received, tokens[0], tokens[5]);

					if (sent) {
						if (haspic) {
							BufferedImage img = null;
							try {
								File imgfile = new File("config/user/" + u.getNome() + "/" + "/pics/" + tokens[5] + ".jpg");
								img = ImageIO.read(imgfile);
							} catch (IOException e) {
							}
							mensagem.setHaspic(haspic);
							ImageIcon icon = new ImageIcon(img);
							this.conversa.sendImg(icon);
						} else {
							this.conversa.sendMessage(mensagem, true);
						}

					} else {
						if (haspic) {
							BufferedImage img = null;
							try {
								File imgfile = new File("config/user/" + u.getNome() + "/pics/" + tokens[5] + ".jpg");
								img = ImageIO.read(imgfile);
							} catch (IOException e) {
							}
							mensagem.setHaspic(haspic);
							ImageIcon icon = new ImageIcon(img);
							this.conversa.receiveImg(icon,remetente);
						} else {
							this.conversa.receiveMessage(mensagem.getConteudo(), remetente);
						}
					}
				}

				this.historico.add(mensagem);
			}
			read.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Altera a cor da mensagem quando é recebida pelo outro
	public void setSent(Mensagem m) {
		for (Mensagem msg : historico) {
			if (m.getId().equals(msg.getId())) {
				msg.setReceived(true);
				// altera a bolha
				conversa.setSent(m);
			}
		}
	}

	// Guarda todas as mensagens quando a janela é fechada
	public void saveConversa(Contacto remetente) throws IOException {
		FileWriter writer = new FileWriter("config/user/" + u.getNome() + "/" + remetente.getNome() + "-conversa.txt");
		writer.close();

		for (Mensagem m : historico) {
			System.out.println(m.isReceived());
			writeToFile(m);
		}
	}

}
