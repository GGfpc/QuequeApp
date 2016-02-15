package GUI;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Projecto.Contacto;
import Projecto.Conversa;
import Projecto.Grupo;

public class Utilizador implements Serializable {

	private transient ArrayList<Contacto> contactosDoUtilizador;
	private String nome;
	private ImageIcon pic;

	public Utilizador(String nome) {
		System.out.println(nome);
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
	
	
	public void removeGrupo(Grupo g) {
		contactosDoUtilizador.remove(g);

		File grupos = new File("config/user/" + nome + "/grupos.txt");
		File conversa = new File("config/user/" + nome + "/" + g.getNome()
				+ "-conversa.txt");
		File imagem = new File("config/user/" + nome + "/" + g.getNome()
				+ ".jpg");

		File tempFile = new File(grupos.getAbsolutePath() + ".tmp");

		try {
			BufferedReader read = new BufferedReader(new FileReader(grupos));
			PrintWriter write = new PrintWriter(new FileWriter(tempFile));
			String linha = null;
			while ((linha = read.readLine()) != null) {
				String[] tokens = linha.split(";");
				if (!tokens[0].equals(g.getNome())) {
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
		grupos.delete();
		tempFile.renameTo(grupos);
		conversa.delete();
		imagem.delete();

	}

	public void novoContacto(Contacto remetente) {
		contactosDoUtilizador.add(remetente);
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
	
	public void novoGrupo(Grupo grupo){
		contactosDoUtilizador.add(grupo);
		try {
			File conversa = new File("config/user/" + nome + "/"
					+ grupo.getNome() + "-conversa.txt");
			conversa.createNewFile();
			
			String grupolog = grupo.getNome();
			
			for(Contacto c : grupo.getContactos()){
				grupolog += (";" + c.getNome());
			}

			FileWriter writer = new FileWriter("config/user/" + nome
					+ "/grupos.txt", true);
			
			writer.write(grupolog + System.lineSeparator());
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
		File picDir = new File("config/user/" + nome + "/pics");
		userDir.mkdirs();
		picDir.mkdirs();

		File configFile = new File("config/user/" + nome + "/contactos.txt");
		File configGroupFile = new File("config/user/" + nome + "/grupos.txt");
		try {
			configFile.createNewFile();
			configGroupFile.createNewFile();

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
		}
		

		Contacto c = new Contacto(s,
				new Conversa(new JanelaDeConversa(s), this));
		contactosDoUtilizador.add(c);
		return c;
	}
	
	public Grupo loadGrupo(String s, ArrayList<Contacto> contacto){

		Grupo g = new Grupo(s,
				new Conversa(new JanelaDeConversa(s), this));
		g.setContactos(contacto);
		contactosDoUtilizador.add(g);
		System.out.println(g.getContactos());
		System.out.println(g.getNome());
		return g;
	}

	//Devolve o contacto com esse nome e se não existir cria um
	public Contacto getContacto(String nome) {
		for (Contacto c : contactosDoUtilizador) {
			if (c.getNome().equals(nome)) {
				return c;
			}
		}
		Contacto newcontact = new Contacto(nome, new Conversa(
				new JanelaDeConversa(nome), this));
		contactosDoUtilizador.add(newcontact);
		return newcontact;
	}

	public Grupo getGrupo(String nome) {
		for (Contacto c : contactosDoUtilizador) {
			if (c.getNome().equals(nome) && c instanceof Grupo) {
				return (Grupo) c;
			}
		}
		Grupo newgroup = new Grupo(nome, new Conversa(
				new JanelaDeConversa(nome), this));
		contactosDoUtilizador.add(newgroup);
		return newgroup;
	}

	public void setPic(ImageIcon pic) {
		this.pic = pic;
	}

	public ImageIcon getPic() {
		return pic;
	}
	
	public boolean containsContacto(String c){
		for(Contacto cont : contactosDoUtilizador){
			if(cont.getNome().equals(c)){
				return true;
			}
		}
		return false;
	}

}
