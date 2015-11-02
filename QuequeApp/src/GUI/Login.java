package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
					ViewGUI view = new ViewGUI(utilizador);
					view.open();
					logMenu.dispose();
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

}
