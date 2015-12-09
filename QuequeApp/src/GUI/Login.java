package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Rede.Client;

public class Login extends JFrame {

	private JFrame logMenu = new JFrame("Login");

	// Zona de login
	private JButton login;

	// Zona de username

	private String nome;
	private JTextField username;
	private JLabel user;

	public Login() {

		login = new JButton("Login");

		// Listener para o bot�o de login
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!username.getText().isEmpty()) {
					nome = username.getText();
					Utilizador utilizador = new Utilizador(nome);
					Client client = new Client(utilizador);
					ViewGUI view = new ViewGUI(utilizador, client);
					File novo = new File("config/user/" + nome + "/" + "userpic.jpg");
					if (!novo.exists()) {
						selectPic(utilizador);
					}
					view.open();
					logMenu.dispose();
					client.ligaAServ();
				}

			}
		});

		// Painel principal com o painel username e painel do botao login
		JPanel fields = new JPanel();

		// Painel do botão
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.add(login);

		// Painel do username
		JPanel userField = new JPanel();
		username = new JTextField(30);
		user = new JLabel("Username: ");
		userField.setLayout(new FlowLayout());
		userField.add(user);
		userField.add(username);

		// Adicionar os paineis no painel principal
		fields.setLayout(new BorderLayout());
		fields.add(userField, BorderLayout.NORTH);
		fields.add(buttons, BorderLayout.CENTER);

		// Adicionar o painel principal ao frame
		logMenu.add(fields, BorderLayout.CENTER);
		logMenu.pack();
		logMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void open() {
		logMenu.setLocationRelativeTo(null);
		logMenu.setVisible(true);

	}

	private void selectPic(Utilizador user) {
		File ficheiro;
		Image image = null;
		ImageIcon iconeContacto = null;

		// Mostra um menu a perguntar se quer adicionar foto
		int escolherPic = JOptionPane.showConfirmDialog(null,
				"Deseja adicionar uma foto?");

		// Se a resposta for sim
		if (escolherPic == JOptionPane.YES_OPTION) {
			// Cria filtro para mostrar apenas imagens no selector de ficheiros
			FileNameExtensionFilter filtroImagens = new FileNameExtensionFilter(
					"Image Files", "jpg", "jpeg", "png");

			// Cria selector, adiciona o filtro e mostra os ficheiros
			JFileChooser selectimg = new JFileChooser();
			selectimg.setFileFilter(filtroImagens);
			int result = selectimg.showOpenDialog(null);

			// Se selecionar um ficheiro guarda-o e depois tenta l�r como
			// imagem
			if (result == JFileChooser.APPROVE_OPTION) {
				ficheiro = selectimg.getSelectedFile();

				Path origem = Paths.get(ficheiro.getAbsolutePath());
				
				Path destino = Paths.get("config/user/" + user.getNome() + "/"
						+ "userpic.jpg");
			
				
				

				try {
					Files.copy(origem, destino);
					image = ImageIO.read(ficheiro);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Transforma uma vers�o diminuida da imagem num ImageIcon
				// para o contacto
				iconeContacto = new ImageIcon(new ImageIcon(image).getImage()
						.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));

			} else {
				iconeContacto = new ImageIcon(new ImageIcon(getClass()
						.getResource("/def.png")).getImage().getScaledInstance(
						45, 45, java.awt.Image.SCALE_SMOOTH));
			}
		} else {
			// Transforma a imagem default num ImageIcon para o contacto
			iconeContacto = new ImageIcon(new ImageIcon(getClass().getResource(
					"/def.png")).getImage().getScaledInstance(45, 45,
					java.awt.Image.SCALE_SMOOTH));
		}
		user.setPic(iconeContacto);
		
	}

}
