package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

import Projecto.Contacto;


public class JanelaDeConversa extends JPanel {

	private String nome;
	private static final int PREF_W = 400;
	private static final int PREF_H = 500;
	private JPanel msgHoldingPanel = new JPanel();
	private JScrollPane scroll;

	public JanelaDeConversa(String nome) {
	
		msgHoldingPanel.setLayout(new BoxLayout(msgHoldingPanel, BoxLayout.PAGE_AXIS));
		setLayout(new BorderLayout());
		add(msgHoldingPanel, BorderLayout.PAGE_START);
		this.nome = nome;
		
	}
	
	public void receiveMessage(String s, Contacto c) {
		
		JPanel painel = new JPanel();
		painel.setLayout(new BoxLayout(painel, BoxLayout.LINE_AXIS));
		
		JTextArea msg = new JTextArea();

		msg.setText(s);
		Color bg = Color.decode("#AFEEEE");
		msg.setBackground(bg);
		msg.setEditable(false);

		if (msg.getPreferredSize().width >= (PREF_W )) {
			
			msg.setBorder(new RoundedBorder());
			msg.setLineWrap(true);
			msg.setWrapStyleWord(true);
			
		} else {
			int left = PREF_W - msg.getPreferredSize().width;
			msg.setBorder(new CompoundBorder(new BorderVazia(0, 0, 0, left),
					new RoundedBorder()));
			msg.setMaximumSize(msg.getPreferredSize());
		}
		JLabel fotoDeContacto = new JLabel();
		fotoDeContacto.setIcon(c.getImg());
		painel.add(fotoDeContacto);
		
		painel.add(msg);
		msgHoldingPanel.add(painel);
		msgHoldingPanel.add(Box.createRigidArea(new Dimension(0,20)));
		this.revalidate();
	}
	
	public void sendMessage(String s) {
		msgHoldingPanel.add(Box.createRigidArea(new Dimension(0,10)));
		JPanel painel = new JPanel();
		painel.setLayout(new BoxLayout(painel, BoxLayout.LINE_AXIS));
		JTextArea msg2 = new JTextArea();
		msg2.setEditable(false);
		msg2.setText(s);
		msg2.setBorder(new RoundedBorder());
		Color bg3 = Color.decode("#BADBAD");
		
		msg2.setBackground(bg3);
		
		if (msg2.getPreferredSize().width >= PREF_W) {
			System.out.println("yes");
			msg2.setLineWrap(true);
			msg2.setWrapStyleWord(true);
			
		} else {
			int right = PREF_W - msg2.getPreferredSize().width + 60;
			msg2.setBorder(new CompoundBorder(new BorderVazia(0, right, 0, 0),
					new RoundedBorder()));
			msg2.setMaximumSize(msg2.getPreferredSize());
		}
		painel.add(msg2);
		msgHoldingPanel.add(painel);
		//this.invalidate();
		//this.validate();
		this.revalidate();
		//this.repaint();
		
	}

	
	@Override
	public String toString() {
		return nome;
	}
	



}