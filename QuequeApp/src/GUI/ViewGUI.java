package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ViewGUI {
	
	private JFrame window = new JFrame("HeyApp");
	private JTextField texto = new JTextField(30);
	private JButton send = new JButton("send");
	private JTextArea chat = new JTextArea();
	private JScrollPane scrollpaneChat = new JScrollPane(chat);
	private DefaultListModel model = new DefaultListModel<String>(); 
	private JList <String> list = new JList<>(model);
	private JScrollPane scrollpaneContacts = new JScrollPane(list);
	
	public ViewGUI() {
		
		model.addElement("Ana Alves");
		model.addElement("Bruno Bernardo");
		model.addElement("Carlos Costa");
		
		JPanel textfield = new JPanel();
		textfield.setLayout(new FlowLayout());
		textfield.add(texto);
		textfield.add(send);
		
	
		chat.setEditable(false);
		window.add(scrollpaneContacts, BorderLayout.WEST);
		window.add(scrollpaneChat, BorderLayout.CENTER);
		window.add(textfield, BorderLayout.SOUTH);
		window.setBounds(20, 20, 500, 500);
		//window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public void open() {
			window.setVisible(true);
	}
	
}
