package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.SliderUI;

import Projecto.Contacto;
import Projecto.Conversa;
import Projecto.Grupo;
import Projecto.Mensagem;
import Projecto.MensagemDeGrupo;
import Projecto.MensagemIndividual;
import Rede.Client;

public class ViewGUI {

	private JFrame window = new JFrame("HeyApp");
	private Utilizador user;
	private Client client;
	private boolean online;

	private JPanel userpanel;
	private JLabel userpic;

	private JLabel userLabel;
	private JToggleButton connect;

	// Zona de escrita
	private JTextField texto;
	private JButton send;
	private JButton sendpic;

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
		online = false;

		userLabel = new JLabel(user.getNome());
		userpic = new JLabel();
		userpanel = new JPanel();
		BoxLayout layot = new BoxLayout(userpanel, BoxLayout.LINE_AXIS);
		userpanel.setLayout(layot);

		connect = new JToggleButton("Connect");

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("config/user/" + user.getNome() + "/" + "userpic.jpg"));
		} catch (IOException e) {
		}

		ImageIcon icon = new ImageIcon(img.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));
		user.setPic(icon);
		userpic.setIcon(icon);
		userpic.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				ImageIcon icon = choosePic();
				if (icon != null) {
					icon = new ImageIcon(icon.getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));
					user.setPic(icon);
					userpic.setIcon(icon);
					RenderedImage rend;
					BufferedImage bimage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
					Graphics2D g = bimage.createGraphics();
					g.drawImage(icon.getImage(), 0, 0, null);
					g.dispose();
					rend = bimage;

					File imagem = new File("config/user/" + user.getNome() + "/" + "userpic.jpg");
					try {
						ImageIO.write(rend, "jpg", imagem);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

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
						model.getElementAt(i).getConversa().saveConversa(model.getElementAt(i));
					}
					if (online) {
						client.closeSocket();
					}
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
		sendpic = new JButton("Send Pic");

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

		sendpic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = choosePic();
				if (icon != null) {
					enviaImagem(icon);

				}
			}

		});

		// Adicionar elementos ao painel da Zona de escrita
		JPanel zonaDeEscrita = new JPanel();
		zonaDeEscrita.setLayout(new FlowLayout());
		zonaDeEscrita.add(texto);
		zonaDeEscrita.add(send);
		zonaDeEscrita.add(sendpic);

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
		scrollpaneChat.getVerticalScrollBar().setValue(scrollpaneChat.getVerticalScrollBar().getMaximum());

		// adicionar elementos ao painel do chat
		fundoChat.add(nomeConversa, BorderLayout.NORTH);
		fundoChat.add(zonaDeEscrita, BorderLayout.SOUTH);

		// ******** Zona de Cntactos **********************************/

		zonaDeContactos = new JPanel(new BorderLayout());

		model = new DefaultListModel<Contacto>();
		list = new JList<>(model);

		// Classe que define a aparï¿½ncia dos Elementos na Lista
		class ContactoCellRenderer extends JLabel implements ListCellRenderer<Contacto> {

			@Override
			public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto value, int index,
					boolean isSelected, boolean cellHasFocus) {
				// Cada elemento da lista ï¿½ uma JLabel
				String name = value.getNome();

				if (value.getNotifications() > 0) {
					name = value.getNome() + " (" + value.getNotifications() + ")";
				}
				setText(name);
				setIcon(value.getImg());
				setSize(300, 100);

				Color foreground = Color.WHITE;

				if (value.isOnline()) {
					foreground = Color.decode("#DCFBE8");

				}

				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
					value.setNotifications(0);
					if (value.isOnline()) {
						setText(name + " (Online)");
					}
				}

				else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
					setBackground(foreground);
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
					if (list.getSelectedValue() instanceof Grupo) {
						user.removeGrupo((Grupo) list.getSelectedValue());
					} else {
						user.removeContacto(list.getSelectedValue());
					}
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

				if (grupo.size() < 2) {
					JOptionPane.showMessageDialog(null, "O Grupo tem que ter pelo menos dois contactos");
				} else {

					String nome = JOptionPane.showInputDialog(null, "Nome do Grupo");

					Grupo novo = new Grupo(nome, new Conversa(new JanelaDeConversa(nome), user));
					model.addElement(novo);
					for (Contacto c : grupo) {
						novo.addContacto(c);
					}

					System.out.println(novo.getContactos());
					user.novoGrupo(novo);
					ImageIcon icon = choosePic();
					if (icon != null) {
						novo.setImg(new ImageIcon(icon.getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH)));
					}
					novo.saveImage(user.getNome(), novo.getImg());
				}
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
					chat = (list.getSelectedValue()).getConversa().getConversa();
					scrollpaneChat.setViewportView(chat);
					if (list.getSelectedValue() instanceof Grupo) {
						Grupo g = (Grupo) list.getSelectedValue();
						nomeConversa.setToolTipText(g.getContactos().toString());
					}

					scrollpaneChat.setBorder(BorderFactory.createEmptyBorder());
					scrollpaneChat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					scrollpaneChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

					fundoChat.add(scrollpaneChat, BorderLayout.CENTER);
					scrollpaneChat.setBackground(scrollpaneChat.getParent().getBackground());
					chat.repaint();

					// Volta a permitir escrever e enviar texto
					if (online) {
						send.setEnabled(true);
						texto.setEnabled(true);
					}
				}

			}
		});
		// Impede o envio e escrita de texto quando nenhum contacto estï¿½
		// selecionado
		send.setEnabled(false);
		texto.setEnabled(false);

		// ****************************************************************/

		userpanel.add(userpic);

		userpanel.add(Box.createRigidArea(new Dimension(7, 15)));
		userpanel.add(userLabel);
		userLabel.setFont(new Font("Arial", Font.BOLD, 15));
		userpanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		zonaDeContactos.add(userpanel, BorderLayout.NORTH);
		// window.add(userpanel, BorderLayout.NORTH);
		window.add(zonaDeContactos, BorderLayout.WEST);
		window.add(fundoChat, BorderLayout.CENTER);
		window.setSize(730, 650);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setView(this);
	}

	// Funï¿½ï¿½o para criar contacto
	private void criaContacto(String nome) {

		Contacto novo = new Contacto(nome, new Conversa(new JanelaDeConversa(nome), user));
		model.addElement(novo);
		user.novoContacto(novo);
		novo.saveImage(user.getNome(), novo.getImg());
	}

	private ImageIcon choosePic() {
		File ficheiro;
		Image image = null;

		// Mostra um menu a perguntar se quer adicionar foto
		int escolherPic = JOptionPane.showConfirmDialog(null, "Deseja adicionar uma foto?");

		// Se a resposta for sim
		if (escolherPic == JOptionPane.YES_OPTION) {
			// Cria filtro para mostrar apenas imagens no selector de
			// ficheiros
			FileNameExtensionFilter filtroImagens = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png");

			// Cria selector, adiciona o filtro e mostra os ficheiros
			JFileChooser selectimg = new JFileChooser();
			selectimg.setFileFilter(filtroImagens);
			int result = selectimg.showOpenDialog(null);

			// Se selecionar um ficheiro guarda-o e depois tenta lï¿½r
			// como
			// imagem
			if (result == JFileChooser.APPROVE_OPTION) {
				ficheiro = selectimg.getSelectedFile();
				try {
					image = ImageIO.read(ficheiro);
				} catch (IOException e2) {
				}
				ImageIcon icon = new ImageIcon(image);
				return icon;
			}
		}
		return null;
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
			if (!(list.getSelectedValue() instanceof Grupo)) {
				m = new MensagemIndividual(texto.getText(), list.getSelectedValue(), user, true);
			} else {
				Grupo g = (Grupo) list.getSelectedValue();
				m = new MensagemDeGrupo(texto.getText(), g.getContactos(), user, g, true);
			}
			client.enviaParaServ(m);
			(list.getSelectedValue()).getConversa().novaMensagem(m);
			chat.sendMessage(m, false);
			texto.setText(null);
		}
	}

	private void enviaImagem(ImageIcon img) {
		Mensagem m;
		if (!(list.getSelectedValue() instanceof Grupo)) {
			m = new MensagemIndividual(texto.getText(), list.getSelectedValue(), user, true);
		} else {
			Grupo g = (Grupo) list.getSelectedValue();
			m = new MensagemDeGrupo(texto.getText(), g.getContactos(), user, g, true);
		}

		m.setPic(img);
		m.setHaspic(true);
		client.enviaParaServ(m);
		(list.getSelectedValue()).getConversa().novaMensagem(m);
		m.storePic(img, m.getId());
		(list.getSelectedValue()).getConversa().getConversa().sendImg(img);
	}

	public void open() {
		loadConfig();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		setOnline(false);
		new Thread() {
			@Override
			public void run() {
				while (!online) {
					client.ligaAServ();
				}
			}
		}.start();

	}

	public void setTituloDoFrame(String nome) {
		window.setTitle("HeyAPP - " + nome);

	}

	private void loadConfig() {
		try {

			// Image img = new
			// ImageIcon(ViewGUI.class.getResource("config/user/Gonçalo/userpic.jpg")).getImage();

			BufferedReader read = new BufferedReader(new FileReader("config/user/" + user.getNome() + "/contactos.txt"));
			String line;

			while ((line = read.readLine()) != null) {
				System.out.println(line);
				Contacto c = user.loadContacto(line);

				BufferedImage img = null;
				try {
					img = ImageIO.read(new File("config/user/" + user.getNome() + "/" + c.getNome() + ".jpg"));
				} catch (IOException e) {
				}
				ImageIcon icon = new ImageIcon(img);
				c.setImg(icon);

				c.getConversa().loadConversa(c);
				model.addElement(c);
			}
			read.close();

			BufferedReader readGrupo = new BufferedReader(new FileReader("config/user/" + user.getNome() + "/grupos.txt"));
			String lineGrupos;

			while ((lineGrupos = readGrupo.readLine()) != null) {
				String[] grupoinfo = lineGrupos.split(";");
				ArrayList<Contacto> contacts = new ArrayList<>();
				for (int i = 1; i < grupoinfo.length; i++) {
					contacts.add(user.getContacto(grupoinfo[i]));
				}
				Grupo g = user.loadGrupo(grupoinfo[0], contacts);

				BufferedImage img = null;
				try {
					img = ImageIO.read(new File("config/user/" + user.getNome() + "/" + g.getNome() + ".jpg"));
				} catch (IOException e) {
				}
				ImageIcon icon = new ImageIcon(img);
				g.setImg(icon);

				System.out.println(lineGrupos);

				g.getConversa().loadConversa(g);
				model.addElement(g);
			}
			readGrupo.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setOnline(boolean online) {
		texto.setEnabled(online);
		send.setEnabled(online);
		sendpic.setEnabled(online);
		this.online = online;
		if (online) {
			userLabel.setText(user.getNome() + " (Online)");
		} else {
			userLabel.setText(user.getNome() + " (Offline)");
		}

	}

}
