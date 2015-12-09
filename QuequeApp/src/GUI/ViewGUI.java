package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import Projecto.Contacto;
import Projecto.Conversa;
import Projecto.Grupo;
import Projecto.Mensagem;
import Rede.Client;

public class ViewGUI {

	private JFrame window = new JFrame("HeyApp");
	private Utilizador user;
	private Client client;

	private JPanel userpanel;
	private JLabel userLabel;

	// Zona de escrita
	private JTextField texto;
	private JButton send;

	// Zona de mensagens
	private JPanel fundoChat;
	private JanelaDeConversa chat;
	private JScrollPane scrollpaneChat;
	private JLabel empty;

	// Zona de contactos
	private JPanel zonaDeContactos;
	private DefaultListModel<Contacto> model;
	private JList<Contacto> list;
	private JScrollPane scrollpaneContacts;
	private JButton addContacto;
	private JButton deleteContacto;
	private JButton addGrupo;

	public ViewGUI(Utilizador user, Client client) {

		this.user = user;
		this.client = client;
		userLabel = new JLabel(user.getNome() + "aquiii");
		userpanel = new JPanel();
		userLabel.setIcon(user.getPic());
	
		
		
		window.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				try {

					for (int i = 0; i < model.getSize(); i++) {
						model.getElementAt(i).getConversa()
								.saveConversa(model.getElementAt(i));
					}

					client.closeSocket();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// **************** Zona de Escrita **************************/

		texto = new JTextField(30);
		send = new JButton("send");

		// Listener para botï¿½o Enviar
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enviaMensagem();
			}
		});
		// Listender do TextField para tecla Enter
		texto.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enviaMensagem();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Adicionar elementos ao painel da Zona de escrita
		JPanel zonaDeEscrita = new JPanel();
		zonaDeEscrita.setLayout(new FlowLayout());
		zonaDeEscrita.add(texto);
		zonaDeEscrita.add(send);

		// ******** Zona de Chat ***************************************/

		fundoChat = new JPanel(new BorderLayout());

		// Cria label com para pï¿½r o nome do Contacto e alinha o texto
		// horizontalmente
		JLabel nomeConversa = new JLabel("Chat");
		nomeConversa.setHorizontalAlignment(SwingConstants.CENTER);
		empty = new JLabel("Seleciona um contacto");
		empty.setHorizontalAlignment(SwingConstants.CENTER);
		fundoChat.add(empty, BorderLayout.CENTER);
		scrollpaneChat = new JScrollPane(chat);
		scrollpaneChat.getVerticalScrollBar().setValue(
				scrollpaneChat.getVerticalScrollBar().getMaximum());

		// adicionar elementos ao painel do chat
		fundoChat.add(nomeConversa, BorderLayout.NORTH);
		fundoChat.add(zonaDeEscrita, BorderLayout.SOUTH);

		// ******** Zona de Contactos **********************************/

		zonaDeContactos = new JPanel(new BorderLayout());

		model = new DefaultListModel<Contacto>();
		list = new JList<>(model);

		// Classe que define a aparï¿½ncia dos Elementos na Lista
		class ContactoCellRenderer extends JLabel implements
				ListCellRenderer<Contacto> {

			@Override
			public Component getListCellRendererComponent(
					JList<? extends Contacto> list, Contacto value, int index,
					boolean isSelected, boolean cellHasFocus) {
				// Cada elemento da lista ï¿½ uma JLabel
				String name = value.getNome();

				if (value.getNotifications() > 0) {
					name = value.getNome() + " (" + value.getNotifications()
							+ ")";
				}
				setText(name);
				setIcon(value.getImg());
				Font font = new Font("Arial", Font.BOLD, 30);
				setFont(font);

				setSize(300, 100);

				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
					value.setNotifications(0);
				}

				else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
					if (value.getNotifications() > 0) {
						setBackground(Color.GREEN);
					}
				}

				setEnabled(list.isEnabled());
				setFont(list.getFont());
				setOpaque(true);

				return this;
			}
		}
		// Adiciona novo CellRenderer ï¿½ lista e limita a largura maxima
		list.setCellRenderer(new ContactoCellRenderer());
		list.setFixedCellWidth(175);

		scrollpaneContacts = new JScrollPane(list);
		scrollpaneContacts.setBorder(BorderFactory.createEmptyBorder());
		addContacto = new JButton("Novo");

		// Listener para o botï¿½o de adicionar contactos
		addContacto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Mostra um menu a perguntar o nome
				String name = JOptionPane.showInputDialog("Indique o nome");
				if (name != null) {
					criaContacto(name);
				}

			}

		});

		deleteContacto = new JButton("Apagar");

		// Listener para o botï¿½o de apagar contactos
		deleteContacto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex() != -1 && !list.isSelectionEmpty()) {
					int index = list.getSelectedIndex();
					user.removeContacto(list.getSelectedValue());
					fundoChat.remove(scrollpaneChat);
					fundoChat.add(empty, BorderLayout.CENTER);
					list.clearSelection();
					model.remove(index);
					fundoChat.repaint();
					texto.setEnabled(true);
					send.setEnabled(true);
				}
				texto.setEnabled(false);
				send.setEnabled(false);
			}
		});

		addGrupo = new JButton("Grupo");
		addGrupo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Contacto> grupo = list.getSelectedValuesList();

				Grupo novo = new Grupo("Nome", new Conversa(
						new JanelaDeConversa("Grupo"), user));
				model.addElement(novo);
				for (Contacto c : grupo) {
					novo.addContacto(c);
				}

				System.out.println(novo.getContactos());
				user.novoGrupo(novo);
			}
		});

		// Adiciona elementos ï¿½ zona dos contactos
		JPanel zonaDeEditar = new JPanel();
		zonaDeEditar.setLayout(new FlowLayout());
		zonaDeEditar.setBackground(Color.white);
		zonaDeEditar.add(addContacto);
		zonaDeEditar.add(addGrupo);
		zonaDeEditar.add(deleteContacto);
		zonaDeContactos.add(zonaDeEditar, BorderLayout.SOUTH);
		zonaDeContactos.add(scrollpaneContacts, BorderLayout.CENTER);
		zonaDeContactos.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// Listener para o elemento selecionado da Lista
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Muda a JLabel com o nome da conversa para o nome do
				// Contacto
				if (!list.isSelectionEmpty()) {
					nomeConversa.setText(list.getSelectedValue().getNome());
					chat = (list.getSelectedValue()).getConversa()
							.getConversa();
					scrollpaneChat.setViewportView(chat);

					scrollpaneChat.setBorder(BorderFactory.createEmptyBorder());
					scrollpaneChat
							.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					scrollpaneChat
							.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

					fundoChat.add(scrollpaneChat, BorderLayout.CENTER);
					scrollpaneChat.setBackground(scrollpaneChat.getParent()
							.getBackground());
					chat.repaint();

					// Volta a permitir escrever e enviar texto
					send.setEnabled(true);
					texto.setEnabled(true);
				}

			}
		});
		// Impede o envio e escrita de texto quando nenhum contacto estï¿½
		// selecionado
		send.setEnabled(false);
		texto.setEnabled(false);

		// ****************************************************************/

		
		
		
		userpanel.add(userLabel);
		window.add(userpanel, BorderLayout.NORTH);
		window.add(zonaDeContactos, BorderLayout.WEST);
		window.add(fundoChat, BorderLayout.CENTER);
		window.setSize(720, 650);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setView(this);
	}

	// Funï¿½ï¿½o para criar contacto
	private void criaContacto(String nome) {

		Contacto novo = new Contacto(nome, new Conversa(new JanelaDeConversa(
				nome), user));
		model.addElement(novo);
		user.novoContacto(novo);

	}

	public DefaultListModel<Contacto> getModel() {
		return model;
	}

	public JList<Contacto> getList() {
		return list;
	}

	// Escreve o texto no chat e adiciona ï¿½ ArrayList de mensagens do Contacto
	private void enviaMensagem() {
		if (!texto.getText().isEmpty()) {
			Mensagem m;

			if (list.getSelectedValue() instanceof Grupo) {
				m = new Mensagem(texto.getText(), list.getSelectedValue(),
						true, user,true);
				m.setJanela(list.getSelectedValue());
				System.out.println("grupo");
			} else {

				m = new Mensagem(texto.getText(), list.getSelectedValue(),
						true, user,false);
				m.setJanela(list.getSelectedValue());
				System.out.println("contacto");
			}
			client.enviaParaServ(m);
			(list.getSelectedValue()).getConversa().novaMensagem(m);
			chat.sendMessage(m, false);
			texto.setText(null);

		}

	}

	public void open() {
		loadConfig();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public void setTituloDoFrame(String nome) {
		window.setTitle("HeyAPP - " + nome);

	}

	private void loadConfig() {
		try {

			// Image img = new
			// ImageIcon(ViewGUI.class.getResource("config/user/Gonçalo/userpic.jpg")).getImage();

			BufferedReader read = new BufferedReader(new FileReader(
					"config/user/" + user.getNome() + "/contactos.txt"));
			String line;

			while ((line = read.readLine()) != null) {
				System.out.println(line);
				Contacto c = user.loadContacto(line);
				c.getConversa().loadConversa(c);
				model.addElement(c);
			}
			read.close();
			
			BufferedReader readGrupo = new BufferedReader(new FileReader(
					"config/user/" + user.getNome() + "/grupos.txt"));
			String lineGrupos;

			while ((lineGrupos = readGrupo.readLine()) != null) {
				System.out.println(lineGrupos);
				Grupo g = user.loadGrupo(lineGrupos);
				g.getConversa().loadConversa(g);
				model.addElement(g);
			}
			readGrupo.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
