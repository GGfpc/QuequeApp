package GUI;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Projecto.Contacto;
import Projecto.Conversa;
import Projecto.Mensagem;

public class Utilizador implements Serializable{

	private transient ArrayList<Contacto> contactosDoUtilizador;
	private String nome;
	private ImageIcon pic;
	

	public Utilizador(String nome) {
		contactosDoUtilizador = new ArrayList<>();
		this.nome = nome;
		criaConfig();
	}

	

	public void removeContacto(Contacto c) {
		contactosDoUtilizador.remove(c);

		File contactos = new File("config/user/" + nome + "/contactos.txt");
		File conversa = new File("config/user/" + nome + "/" + c.getNome()
				+ "-conversa.txt");
		File imagem = new File("config/user/" + nome + "/" + c.getNome()
				+ ".jpg");

		File tempFile = new File(contactos.getAbsolutePath() + ".tmp");

		try {
			BufferedReader read = new BufferedReader(new FileReader(contactos));
			PrintWriter write = new PrintWriter(new FileWriter(tempFile));
			String linha = null;
			while ((linha = read.readLine()) != null) {
				if (!linha.trim().equals(c.getNome())) {
					write.println(linha);
					write.flush();
				}
			}
			read.close();
			write.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contactos.delete();
		tempFile.renameTo(contactos);
		conversa.delete();
		imagem.delete();

	}

	public void novoContacto(Contacto remetente) {
		contactosDoUtilizador.add(remetente);
		System.out.println(remetente);
		System.out.println(contactosDoUtilizador);
		try {
			File conversa = new File("config/user/" + nome + "/"
					+ remetente.getNome() + "-conversa.txt");
			conversa.createNewFile();

			FileWriter writer = new FileWriter("config/user/" + nome
					+ "/contactos.txt", true);
			writer.write(remetente.getNome() + System.lineSeparator());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getNome() {
		return nome;
	}

	private void criaConfig() {
		File userDir = new File("config/user/" + nome);
		userDir.mkdirs();

		File configFile = new File("config/user/" + nome + "/contactos.txt");
		try {
			configFile.createNewFile();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Contacto loadContacto(String s) {
		ImageIcon iconeContacto = null;

		File imagem = new File("config/user/" + getNome() + "/" + s + ".jpg");

		if (imagem.isFile()) {
			System.out.println("yes");
			Image icone;

			try {
				icone = ImageIO.read(imagem);
				iconeContacto = new ImageIcon(new ImageIcon(icone).getImage()
						.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			iconeContacto = new ImageIcon(new ImageIcon(getClass().getResource(
					"/def.png")).getImage().getScaledInstance(45, 45,
					java.awt.Image.SCALE_SMOOTH));
		}

		Contacto c = new Contacto(s,
				new Conversa(new JanelaDeConversa(s), this));
		c.setImg(iconeContacto);
		contactosDoUtilizador.add(c);
		return c;
	}

	public Contacto getContacto(String nome){
		for(Contacto c : contactosDoUtilizador){
			if(c.getNome().equals(nome)){
				return c;
			}
		}
		Contacto newcontact = new Contacto(nome,new Conversa(new JanelaDeConversa(nome), this));
		contactosDoUtilizador.add(newcontact);
		return newcontact;
	}
	
	public void setPic(ImageIcon pic) {
		this.pic = pic;
	}
	
	public ImageIcon getPic() {
		return pic;
	}

	

}
