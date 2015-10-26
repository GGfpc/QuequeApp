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

public class ViewGUI {

	private JFrame window = new JFrame("HeyApp");

	// Zona de escrita
	private JTextField texto;
	private JButton send;

	// Zona de mensagens
	private JPanel fundoChat;
	private JTextArea chat;

	// Zona de contactos
	private JPanel zonaDeContactos;
	private DefaultListModel<Contacto> model;
	private JList<Contacto> list;
	private JScrollPane scrollpaneContacts;
	private JButton addContacto;

	public ViewGUI() {

		// **************** Zona de Escrita **************************/

		texto = new JTextField(30);
		send = new JButton("send");

		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chat.setText(chat.getText() + "\n" + "Eu: " + texto.getText());
				texto.setText(null);
			}
		});

		texto.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chat.setText(chat.getText() + "\n" + "Eu:"
							+ texto.getText());
					texto.setText(null);
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

		JPanel zonaDeEscrita = new JPanel();
		zonaDeEscrita.setBackground(Color.WHITE);
		zonaDeEscrita.setLayout(new FlowLayout());
		zonaDeEscrita.add(texto);
		zonaDeEscrita.add(send);

		// ******** Zona de Chat ***************************************/

		fundoChat = new JPanel(new BorderLayout());
		JLabel nomeConversa = new JLabel("Chat");
		nomeConversa.setHorizontalAlignment(SwingConstants.CENTER);

		fundoChat.add(nomeConversa, BorderLayout.NORTH);
		fundoChat.add(zonaDeEscrita, BorderLayout.SOUTH);

		// ******** Zona de Contactos **********************************/

		zonaDeContactos = new JPanel(new BorderLayout());
		model = new DefaultListModel<Contacto>();
		list = new JList<>(model);

		class ContactoCellRenderer extends JLabel implements
				ListCellRenderer<Contacto> {

			@Override
			public Component getListCellRendererComponent(
					JList<? extends Contacto> list, Contacto value, int index,
					boolean isSelected, boolean cellHasFocus) {

				String name = value.getNome();
				setText(name);
				setIcon(value.getImg());
				Font font = new Font("Arial", Font.BOLD, 30);
				setFont(font);
				setHorizontalAlignment(CENTER);
				setVerticalAlignment(CENTER);

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

		list.setCellRenderer(new ContactoCellRenderer());
		list.setFixedCellWidth(175);

		scrollpaneContacts = new JScrollPane(list);

		addContacto = new JButton("Novo");
		addContacto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel dialog = new JPanel(new FlowLayout());
				JButton foto = new JButton("Inserir foto");
				File ficheiro;
				Image image = null;
				ImageIcon iconeContacto = null;
				
				String name = JOptionPane.showInputDialog("Indique o nome");
				int option = JOptionPane.showConfirmDialog(null, "Deseja adicionar uma foto?");
				
				if(option == JOptionPane.YES_OPTION){
					JFileChooser selectimg = new JFileChooser();
					int result = selectimg.showOpenDialog(null);
					
					if(result == JFileChooser.APPROVE_OPTION){
						ficheiro = selectimg.getSelectedFile() ;
						try {
							image = ImageIO.read(ficheiro);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						iconeContacto = new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH));
					}
				}
				model.addElement(new Contacto(name, new Conversa(
						new JTextArea()), iconeContacto));

			}
		});

		zonaDeContactos.add(addContacto, BorderLayout.SOUTH);
		zonaDeContactos.add(scrollpaneContacts, BorderLayout.CENTER);

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				nomeConversa.setText(list.getSelectedValue().getNome());
				chat = list.getSelectedValue().getConversa().getConversa();
				JScrollPane scrollpaneChat = new JScrollPane(chat);
				fundoChat.add(scrollpaneChat, BorderLayout.CENTER);
				send.setEnabled(true);
				texto.setEnabled(true);

			}
		});

		send.setEnabled(false);
		texto.setEnabled(false);

		// ****************************************************************/

		window.add(zonaDeContactos, BorderLayout.WEST);
		window.add(fundoChat, BorderLayout.CENTER);
		window.setSize(700, 500);
		// window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void open() {
		window.setVisible(true);
	}

}
