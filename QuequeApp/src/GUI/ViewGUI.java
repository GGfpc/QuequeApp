package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;


import Projecto.Contacto;
import Projecto.Conversa;
import Projecto.Mensagem;

public class ViewGUI {

	private JFrame window = new JFrame("HeyApp");

	// Zona de escrita
	private JTextField texto;
	private JButton send;

	// Zona de mensagens
	private JPanel fundoChat;
	private JTextArea chat;
	private JScrollPane scrollpaneChat;

	// Zona de contactos
	private JPanel zonaDeContactos;
	private DefaultListModel<Contacto> model;
	private JList<Contacto> list;
	private JScrollPane scrollpaneContacts;
	private JButton addContacto;
	private JButton deleteContacto;

	public ViewGUI() {

		// **************** Zona de Escrita **************************/

		texto = new JTextField(30);
		send = new JButton("send");

		//Listener para bot�o Enviar
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enviaMensagem();
			}
		});
		//Listender do TextField para tecla Enter
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

		//Adicionar elementos ao painel da Zona de escrita
		JPanel zonaDeEscrita = new JPanel();
		zonaDeEscrita.setBackground(Color.WHITE);
		zonaDeEscrita.setLayout(new FlowLayout());
		zonaDeEscrita.add(texto);
		zonaDeEscrita.add(send);

		// ******** Zona de Chat ***************************************/

		fundoChat = new JPanel(new BorderLayout());
		
		//Cria label com para p�r o nome do Contacto e alinha o texto horizontalmente
		JLabel nomeConversa = new JLabel("Chat");
		nomeConversa.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Cria scrollpane para o chat e p�e uma border invisivel
		scrollpaneChat = new JScrollPane(chat);
		scrollpaneChat.setBorder(BorderFactory.createEmptyBorder());

		//adicionar elementos ao painel do chat
		fundoChat.add(nomeConversa, BorderLayout.NORTH);
		fundoChat.add(zonaDeEscrita, BorderLayout.SOUTH);

		// ******** Zona de Contactos **********************************/

		zonaDeContactos = new JPanel(new BorderLayout());
		model = new DefaultListModel<Contacto>();
		list = new JList<>(model);

		//Classe que define a apar�ncia dos Elementos na Lista 
		class ContactoCellRenderer extends JLabel implements
				ListCellRenderer<Contacto> {

			@Override
			public Component getListCellRendererComponent(
					JList<? extends Contacto> list, Contacto value, int index,
					boolean isSelected, boolean cellHasFocus) {
				//Cada elemento da lista � uma JLabel
				String name = value.getNome();
				setText(name);
				setIcon(value.getImg());
				Font font = new Font("Arial", Font.BOLD, 30);
				setFont(font);
			
				setSize(300, 100);

				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				} else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}
				setEnabled(list.isEnabled());
				setFont(list.getFont());
				setOpaque(true);

				return this;
			}
		}
		//Adiciona novo CellRenderer � lista e limita a largura maxima
		list.setCellRenderer(new ContactoCellRenderer());
		list.setFixedCellWidth(175);

		scrollpaneContacts = new JScrollPane(list);
		fundoChat.add(scrollpaneChat, BorderLayout.CENTER);

		addContacto = new JButton("Novo");
		
		//Listener para o bot�o de adicionar contactos
		addContacto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				criaContacto();

			}

		});
		
		deleteContacto = new JButton("Apagar");
		
		//Listener para o bot�o de apagar contactos
		deleteContacto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex() != 0) {
					int index = list.getSelectedIndex();
					list.setSelectedIndex(list.getSelectedIndex() - 1);
					model.remove(index);
					//int size = model.getSize();
					
					//if(size == 0) {
					//	deleteContacto.setEnabled(false);
					//}
					//else {
						//	if(index == model.getSize()) {
						//		index--;
					//		}
					//list.setSelectedIndex(index);
					//list.ensureIndexIsVisible(index);
					//}
				}
			}
		});
		
		//Adiciona elementos � zona dos contactos
				JPanel zonaDeEditar = new JPanel();
				zonaDeEditar.setLayout(new FlowLayout());
				zonaDeEditar.add(addContacto);
				zonaDeEditar.add(deleteContacto);
				zonaDeContactos.add(zonaDeEditar, BorderLayout.SOUTH);
				zonaDeContactos.add(scrollpaneContacts, BorderLayout.CENTER);

		
		//Listener para o elemento selecionado da Lista
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Muda a JLabel com o nome da conversa para o nome do Contacto
				nomeConversa.setText(list.getSelectedValue().getNome());
				//Muda o chat para a JTextArea que pertence ao contacto
				chat = list.getSelectedValue().getConversa().getConversa();
				//Muda a scrollpane para o chat novo
				scrollpaneChat.setViewportView(chat);
				//Volta a permitir escrever e enviar texto
				send.setEnabled(true);
				texto.setEnabled(true);

			}
		});
		//Impede o envio e escrita de texto quando nenhum contacto est� selecionado
		send.setEnabled(false);
		texto.setEnabled(false);

		// ****************************************************************/

		window.add(zonaDeContactos, BorderLayout.WEST);
		window.add(fundoChat, BorderLayout.CENTER);
		window.setSize(800, 600);
		// window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	// Fun��o para criar contacto
	private void criaContacto() {
		File ficheiro;
		Image image = null;
		ImageIcon iconeContacto = null;
		
		//Mostra um menu a perguntar o nome
		String name = JOptionPane.showInputDialog("Indique o nome");
		
		//Mostra um menu a perguntar se quer adicionar foto
		int escolherPic = JOptionPane.showConfirmDialog(null, "Deseja adicionar uma foto?");
		
		//Se a resposta for sim
		if(escolherPic == JOptionPane.YES_OPTION){
			//Cria filtro para mostrar apenas imagens no selector de ficheiros
			FileNameExtensionFilter filtroImagens = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png");
			
			//Cria selector, adiciona o filtro e mostra os ficheiros
			JFileChooser selectimg = new JFileChooser();
			selectimg.setFileFilter(filtroImagens);
			int result = selectimg.showOpenDialog(null);
			
			//Se selecionar um ficheiro guarda-o e depois tenta l�r como imagem
			if(result == JFileChooser.APPROVE_OPTION){
				ficheiro = selectimg.getSelectedFile() ;
				try {
					image = ImageIO.read(ficheiro);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//Transforma uma vers�o diminuida da imagem num ImageIcon para o contacto
				iconeContacto = new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));
			}
		} else {
			//Transforma a imagem default num ImageIcon para o contacto
			iconeContacto = new ImageIcon(new ImageIcon(getClass().getResource(
					"/def.png")).getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));
		}
		//Cria o novo contacto
		model.addElement(new Contacto(name, new Conversa(
				new JTextArea()), iconeContacto));
	}
	
	//Devolve o ultimo contacto criado para adicionar � ArrayList do Utilizador
	public Contacto getNovoContacto() {
		return list.getModel().getElementAt(list.getModel().getSize() - 1);

	}
	//Escreve o texto no chat e adiciona � ArrayList de mensagens do Contacto
	private void enviaMensagem() {
		Mensagem message = new Mensagem(texto.getText(), list.getSelectedValue());
		list.getSelectedValue().getConversa().addMessage(message);
		chat.setText(chat.getText() + "\n" + "Eu: " + message.getConteudo());
		texto.setText(null);
	}

	public void open() {
		window.setVisible(true);
	}

}
