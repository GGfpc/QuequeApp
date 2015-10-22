package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class ViewGUI {
	
	private JFrame window = new JFrame("HeyApp");
	
	//Zona de escrita
	private JTextField texto = new JTextField(30);
	private JButton send = new JButton("send");
	
	//Zona de mensagens
	private JPanel fundoChat;
	private JTextArea chat = new JTextArea();
	private JScrollPane scrollpaneChat = new JScrollPane(chat);
	
	//Zona de contactos
	private JPanel zonaDeContactos;
	private DefaultListModel model = new DefaultListModel<String>(); 
	private JList <String> list = new JList<>(model);
	private JScrollPane scrollpaneContacts = new JScrollPane(list);
	private JButton addContacto;
	
	public ViewGUI() {
		
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chat.setText(chat.getText() + "\n"  + texto.getText());	
				texto.setText(null);
			}
		});
		
		texto.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					chat.setText(chat.getText() + "\n"  + texto.getText());	
					texto.setText(null);
				}
				
			}
		});
		
		
		zonaDeContactos = new JPanel(new BorderLayout());
		
		fundoChat = new JPanel(new BorderLayout());
	
		model.addElement("Ana Alves");
		model.addElement("Bruno Bernardo");
		model.addElement("Carlos Costa");
		
		JPanel zonaDeEscrita = new JPanel();
		zonaDeEscrita.setLayout(new FlowLayout());
		zonaDeEscrita.add(texto);
		zonaDeEscrita.add(send);
		
		addContacto = new JButton("Novo");	
		addContacto.setSize(30,5);
		
		addContacto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog("Nome do Contacto");
				model.addElement(name);
				
			}
		});
		
		zonaDeContactos.add(addContacto, BorderLayout.SOUTH);
		zonaDeContactos.add(scrollpaneContacts,BorderLayout.CENTER);
		
		JLabel nomeConversa = new JLabel("Chat");
		nomeConversa.setHorizontalAlignment(SwingConstants.CENTER);
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				nomeConversa.setText(list.getSelectedValue());
				
			}
		});
		
		chat.setEditable(false);
		window.add(zonaDeContactos, BorderLayout.WEST);
		fundoChat.add(nomeConversa, BorderLayout.NORTH);
		fundoChat.add(scrollpaneChat, BorderLayout.CENTER);
		fundoChat.add(zonaDeEscrita, BorderLayout.SOUTH);
		window.add(fundoChat, BorderLayout.CENTER);
		window.setBounds(20, 20, 550, 500);
		//window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public void open() {
			window.setVisible(true);
	}
	
}
